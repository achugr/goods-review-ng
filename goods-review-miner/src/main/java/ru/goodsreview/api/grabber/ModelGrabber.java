package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
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
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public class ModelGrabber extends AbstractGrabber {
    private final static Logger log = Logger.getLogger(ModelGrabber.class);

    private static final Integer COUNT_MAX_VALUE = 30;

    public ModelGrabber() {
        this.entityType = EntityType.MODEL;
    }

    public List<JSONObject> getCategoriesFromDB() {
        log.info("Getting categories from DB started");
        EntityExtractor categoriesExtractor = new EntityExtractor();
        grabberBatch.getEntityService().visitEntities(EntityType.CATEGORY.getTypeId(), categoriesExtractor);
        log.info("Getting categories from DB ended");
        return categoriesExtractor.getEntities();
    }

    public List<JSONObject> grabModelsForCategoriesFromDB(){
        log.info("Grabbing models to DB started");
        List<JSONObject> categoriesFromDB = getCategoriesFromDB();
        return grabModels(categoriesFromDB);
    }

    public List<JSONObject> grabModels(String... categories){
        CategoryGrabber categoryGrabber = setUpCategoryGrabber();
        List<JSONObject> specificCategories = categoryGrabber.grabCategories(categories);
        return grabModels(specificCategories);
    }

    public List<JSONObject> grabAllModels(){
        CategoryGrabber categoryGrabber = setUpCategoryGrabber();
        List<JSONObject> allCategoriesList = categoryGrabber.grabAllCategories();
        return grabModels(allCategoriesList);
    }

    private CategoryGrabber setUpCategoryGrabber() {
        CategoryGrabber categoryGrabber = new CategoryGrabber();
        categoryGrabber.setGrabberBatch(grabberBatch);
        categoryGrabber.setContentApiProvider(contentApiProvider);
        return categoryGrabber;
    }

    private List<JSONObject> grabModels(List<JSONObject> categories){
        List<JSONObject> models = new ArrayList<JSONObject>();
        log.info("Grabbing models to DB started");
        for(JSONObject category : categories){
            try {
                //Here can be thrown JSONException - if there are no such keys in json object "category"
                long categoryId = category.getLong(JSONKeys.ID.getKey());
                int modelsNum = category.getInt(JSONKeys.MODELS_NUM.getKey());

                if(modelsNum != 0){
                    int pageCount = ( modelsNum / COUNT_MAX_VALUE ) + 1;
                    for(Integer pageNum = 1; pageNum <= pageCount; pageNum++){
                        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();

                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put(RequestParams.COUNT.getKey(), COUNT_MAX_VALUE.toString());
                        parameters.put(RequestParams.PAGE.getKey(), pageNum.toString());

                        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfModelsOfCategoryById(categoryId, parameters);
                        try {
                            //Here can be thrown HttpException or IOException - if something wrong in json object downloading
                            JSONObject mainObject = contentApiProvider.provide(urlRequest);

                            //Here can be thrown JSONException - if something wrong with received json object
                            List<JSONObject> modelsList = JSONUtil.extractList(mainObject, JSONKeys.ITEMS.getKey(), JSONKeys.MODELS.getKey());

                            //If everything Ok - processing valid json object
                            processEntityList(modelsList);
                            models.addAll(modelsList);
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
        log.info("Grabbing models to DB ended");
        return models;
    }
}