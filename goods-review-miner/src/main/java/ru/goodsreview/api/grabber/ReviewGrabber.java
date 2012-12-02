package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.api.request.builder.OpinionRequestBuilder;
import ru.goodsreview.api.request.builder.RequestParams;
import ru.goodsreview.api.request.builder.UrlRequest;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.util.JSONUtil;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
 */

public class ReviewGrabber extends AbstractGrabber{
    private static final Logger log = Logger.getLogger(ReviewGrabber.class);

    private static final int MAX_REVIEWS_NUM_PER_PAGE = 30;

    public ReviewGrabber(){
        entityType = EntityType.REVIEW;
    }

    /*
    *Because there are huge number of models in market, it's mine requires a lot of time!
    *In order to save time it is possible to mine models once and further get them from DB
    *and use to mine reviews.
    */
    public void grabReviewsForModelsFromDB(){
        List<JSONObject> modelsFromDB = getModelsFromDB();
        grabReviews(modelsFromDB);
    }

    /*
    *For some purpose it is needed to have reviews on models of some specific categories.
    *This method does this task!
    */
    public void grabReviews(String... categories){
        ModelGrabber modelGrabber = setUpModelGrabber();
        List<JSONObject> specificModels = modelGrabber.grabModels(categories);
        grabReviews(specificModels);
    }

    /*
    *NB: This method at first grabs ALL model from market and then grabs all reviews,
    *so it works really long time!
    */
    public void grabAllReviews(){
        ModelGrabber modelGrabber = setUpModelGrabber();
        List<JSONObject> allModels = modelGrabber.grabAllModels();
        grabReviews(allModels);
    }

    private List<JSONObject> getModelsFromDB() {
        log.info("Getting models from DB started");
        EntityExtractor modelsExtractor = new EntityExtractor();
        grabberBatch.getEntityService().visitEntities(EntityType.MODEL.getTypeId(), modelsExtractor);
        log.info("Getting models from DB ended");
        return modelsExtractor.getEntities();
    }

    private ModelGrabber setUpModelGrabber() {
        ModelGrabber modelGrabber = new ModelGrabber();
        modelGrabber.setGrabberBatch(grabberBatch);
        modelGrabber.setContentApiProvider(contentApiProvider);
        return modelGrabber;
    }

    private void setModelId(List<JSONObject> reviews, long modelId) {
        for(JSONObject review : reviews){
            try {
                review.put("modelId", modelId);
            } catch (JSONException e) {
                log.error("Can't put \"modelId\" to json object " + modelId, e);
            }
        }
    }

    private void setOpinionsCount(JSONObject model, int opinionsCount) throws JSONException {
        model.put("opinionsCount", opinionsCount);
    }

    private void updateInDB(JSONObject model) {
        grabberBatch.getEntityService().improveEntity(model);
    }

    private void update(JSONObject model, int opinionsCount) {
        try {
            setOpinionsCount(model, opinionsCount);
            updateInDB(model);
        } catch (JSONException e) {
            log.error("Can't put \"optionsCount\" to json object " + model, e);
        }
    }

    private void grabReviews(List<JSONObject> models) {
        log.info("Grabbing reviews to DB started");
        for(JSONObject model : models){
            try {
                //Here can be thrown JSONException - if there are no such keys in json object "model"
                long modelId = model.getLong(JSONKeys.ID.getKey());

                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put(RequestParams.COUNT.getKey(), Integer.toString(MAX_REVIEWS_NUM_PER_PAGE));
                parameters.put(RequestParams.PAGE.getKey(), "1");

                OpinionRequestBuilder opinionRequestBuilder = new OpinionRequestBuilder();
                UrlRequest urlRequest = opinionRequestBuilder.requestForOpinionOnModelById(modelId, parameters);

                List<JSONObject> reviewsList = null;
                try {
                    //Here can be thrown HttpException or IOException - if something wrong in json object downloading
                    JSONObject modelOpinions = contentApiProvider.provide(urlRequest);

                    //Here can be thrown JSONException - if something wrong with received json object
                    reviewsList = JSONUtil.extractList(modelOpinions, JSONKeys.OPINION.getKey(), JSONKeys.MODEL_OPINIONS.getKey());

                    //If everything Ok - processing valid json object
                    setModelId(reviewsList, modelId);
                    processEntityList(reviewsList);

                    try{
                        //Here can be thrown JSONException - if there are no such keys in json object "modelOpinions"
                        JSONObject opinionsInnerData = modelOpinions.getJSONObject(JSONKeys.MODEL_OPINIONS.getKey());
                        try{
                            //Here can be thrown JSONException - if there are no such keys in json object "total"
                            int opinionsCount = opinionsInnerData.getInt(JSONKeys.TOTAL.getKey());

                            if(opinionsCount > MAX_REVIEWS_NUM_PER_PAGE){
                                int pageCount = (opinionsCount % MAX_REVIEWS_NUM_PER_PAGE == 0) ? (opinionsCount / MAX_REVIEWS_NUM_PER_PAGE) : (opinionsCount / MAX_REVIEWS_NUM_PER_PAGE) + 1;
                                for(int pageNumber = 2; pageNumber <= pageCount; pageNumber++){

                                    parameters = new HashMap<String, String>();
                                    parameters.put(RequestParams.PAGE.getKey(), Integer.toString(pageNumber));

                                    urlRequest = opinionRequestBuilder.requestForOpinionOnModelById(modelId, parameters);
                                    try{
                                        //Here can be thrown HttpException or IOException - if something wrong in json object downloading
                                        modelOpinions = contentApiProvider.provide(urlRequest);

                                        //Here can be thrown JSONException - if something wrong with received json object
                                        reviewsList = JSONUtil.extractList(modelOpinions, JSONKeys.OPINION.getKey(), JSONKeys.MODEL_OPINIONS.getKey());

                                        //If everything Ok - processing valid json object
                                        setModelId(reviewsList, modelId);
                                        processEntityList(reviewsList);
                                    }catch (JSONException e) {
                                        log.error("Error while parsing json object, received " +
                                                "by url: " + urlRequest.getUrl(), e);
                                    }
                                }
                            }
                            update(model, opinionsCount);
                        } catch (JSONException e) {
                            log.error("Error while parsing json object " + opinionsInnerData + " : no such key \"total\"", e);
                            update(model, 0);
                        }
                    } catch (JSONException e) {
                        log.error("Error while parsing json object " + modelOpinions + " : no such key \"modelOpinions\"", e);
                        update(model, 0);
                    }
                }catch (HTTPException e){
                    log.error("Http error. " + e.getMessage());
                    update(model, 0);
                } catch (IOException e) {
                    log.error("Error in JSON object transfer. " + "\n" +
                            "Request url: " + urlRequest.getUrl());
                    update(model, 0);
                }catch (JSONException e) {
                    log.error("Error while parsing json object, received " +
                            "by url: " + urlRequest.getUrl(), e);
                    update(model, 0);
                }
            } catch (JSONException e) {
                log.error("Error while parsing json object " + model + " : no such key \"model\"", e);
            }
        }
        log.info("Grabbing reviews to DB ended");
    }
}
