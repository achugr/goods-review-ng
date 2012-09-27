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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 29.08.12
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
 */

public class ReviewGrabber extends AbstractGrabber{
    private static final Logger log = Logger.getLogger(ReviewGrabber.class);

    private static final Integer COUNT_MAX_VALUE = 30;

    public ReviewGrabber(){
        entityType = EntityType.REVIEW;
    }

    /*
    *Because there are huge number of models in market, it's mine requires a lot of time!
    *In order to save time it is possible to mine models once and further get them from DB
    *and use to mine reviews.
    */
    public List<JSONObject> grabReviewsForModelsFromDB(){
        List<JSONObject> modelsFromDB = getModelsFromDB();
        return grabReviews(modelsFromDB);
    }

    /*
    *For some purpose it is needed to have reviews on models of some specific categories.
    *This method does this task!
    */
    public List<JSONObject> grabReviews(String... categories){
        ModelGrabber modelGrabber = setUpModelGrabber();
        List<JSONObject> specificModels = modelGrabber.grabModels(categories);
        return grabReviews(specificModels);
    }

    /*
    *NB: This method at first grabs ALL model from market and then grabs all reviews,
    *so it works really long time!
    */
    public List<JSONObject> grabAllReviews(){
        ModelGrabber modelGrabber = setUpModelGrabber();
        List<JSONObject> allModels = modelGrabber.grabAllModels();
        return grabReviews(allModels);
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
                log.error("Error in entity type setting");
                throw new RuntimeException();
            }
        }
    }

    private List<JSONObject> grabReviews(List<JSONObject> models) {
        List<JSONObject> reviews = new ArrayList<JSONObject>();
        log.info("Grabbing reviews to DB started");
        for(JSONObject model : models){
            try {
                //Here can be thrown JSONException - if there are no such keys in json object "model"
                long modelId = model.getLong(JSONKeys.ID.getKey());
                int reviewsCount = model.getInt(JSONKeys.REVIEWS_COUNT.getKey());

                if(reviewsCount != 0){
                    int pageCount = ( reviewsCount / COUNT_MAX_VALUE ) + 1;
                    for(Integer pageNum = 1; pageNum <= pageCount; pageNum++){
                        OpinionRequestBuilder opinionRequestBuilder = new OpinionRequestBuilder();

                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put(RequestParams.COUNT.getKey(), COUNT_MAX_VALUE.toString());
                        parameters.put(RequestParams.PAGE.getKey(), pageNum.toString());

                        UrlRequest urlRequest = opinionRequestBuilder.requestForOpinionOnModelById(modelId, parameters);

                        List<JSONObject> reviewsList = null;
                        try {
                            //Here can be thrown HttpException or IOException - if something wrong in json object downloading
                            JSONObject mainObject = contentApiProvider.provide(urlRequest);

                            //Here can be thrown JSONException - if something wrong with received json object
                            reviewsList = JSONUtil.extractList(mainObject, JSONKeys.OPINION.getKey(), JSONKeys.MODEL_OPINIONS.getKey());

                            //If everything Ok - processing valid json object
                            setModelId(reviewsList, modelId);
                            processEntityList(reviewsList);
                            reviews.addAll(reviewsList);
                        }catch (HTTPException e){
                            log.error("Http error. " + e.getMessage());
                        } catch (IOException e) {
                            log.error("Error in JSON object transfer. " + "\n" +
                                    "Request url: " + urlRequest.getUrl());
                        }catch (JSONException e) {
                            log.error("Error while parsing json object, received " +
                                    "by url: " + urlRequest.getUrl(), e);
                        }
                    }
                }
            } catch (JSONException e) {
                log.error("Error while parsing json object: no such keys", e);
            }
        }
        log.info("Grabbing reviews to DB ended");
        return reviews;
    }
}
