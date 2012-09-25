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
import java.util.*;

/**
 * User: timur
 * Date: 03.08.12
 * Time: 4:06
 */
public class CategoryGrabber extends AbstractGrabber {
    private final static Logger log = Logger.getLogger(CategoryGrabber.class);

    private static final Integer COUNT_MAX_VALUE = 30;

    public CategoryGrabber(){
        this.entityType = EntityType.CATEGORY;
    }

    public List<JSONObject> grabMainCategories() {
        log.info("Grabbing main categories started");
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        Map<String,String> parameters = new HashMap<String, String>();
        parameters.put(RequestParams.COUNT.getKey(), COUNT_MAX_VALUE.toString());
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(parameters);
        List<JSONObject> mainCategories = new ArrayList<JSONObject>();
        try {
            //Here can be thrown HttpException or IOException - if something wrong in json object downloading
            JSONObject mainObject = contentApiProvider.provide(urlRequest);

            //Here can be thrown JSONException - if something wrong with received json object
            mainCategories.addAll(JSONUtil.extractList(mainObject, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey()));
        }catch (HTTPException e){
            log.error("Http error. " + e.getMessage());
        } catch (IOException e) {
            log.error("Error in JSON object transfer. " + "\n" +
                      "Request url: " + urlRequest.getUrl());
        }catch (JSONException e) {
            log.error("Error while parsing json object, received " +
                      "by url: " + urlRequest.getUrl(), e);
        }
        log.info("Grabbing main categories ended");
        return mainCategories;
    }

    public List<JSONObject> grabMainCategoriesToDB(){
        log.info("Grabbing main categories to DB started");
        List<JSONObject> mainCategoriesList = grabMainCategories();
        processEntityList(mainCategoriesList);
        log.info("Grabbing main categories to DB ended");
        return mainCategoriesList;
    }

    private void grabChildCategories(List<JSONObject> parentCategoriesList, List<JSONObject> allChildCategoriesList){
        for(JSONObject category : parentCategoriesList){
            try {
                //Here can be thrown JSONException - if there are no such keys in json object "category"
                int childrenCount = category.getInt(JSONKeys.CHILDREN_COUNT.getKey());
                if(childrenCount != 0){

                    int pageCount = (childrenCount / COUNT_MAX_VALUE) + 1;

                    for(Integer pageNum = 1; pageNum <= pageCount; pageNum++){

                        // Using category's id to construct request for it's child categories
                        long currId = category.getLong(JSONKeys.ID.getKey());

                        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();

                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put(RequestParams.COUNT.getKey(), COUNT_MAX_VALUE.toString());
                        parameters.put(RequestParams.PAGE.getKey(), pageNum.toString());

                        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfChildrenCategoriesById(currId, parameters);
                        try {
                            //Getting child categories of the current category

                            //Here can be thrown HttpException or IOException - if something wrong in json object downloading
                            JSONObject mainObject = getContentApiProvider().provide(urlRequest);

                            //Here can be thrown JSONException - if something wrong with received json object
                            List<JSONObject> childCategoriesList = JSONUtil.extractList(mainObject, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());

                            //If everything Ok - adding all valid child categories to list
                            allChildCategoriesList.addAll(childCategoriesList);
                            //Going on grabbing child categories of the next level
                            grabChildCategories(childCategoriesList, allChildCategoriesList);
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
            }catch (JSONException e) {
                log.error("some problems with json", e);
                throw new RuntimeException();
            }
        }
    }

    public List<JSONObject> grabChildCategories(String... someMainCategoriesNames) {
        List<JSONObject> allMainCategoriesList = grabMainCategories();

        //filter
        List<String> someMainCategoriesNamesList = Arrays.asList(someMainCategoriesNames);
        List<JSONObject> someMainCategories = new ArrayList<JSONObject>();
        for(JSONObject mainCategory : allMainCategoriesList){
            try {
                if(someMainCategoriesNamesList.contains(mainCategory.getString("name"))){
                    someMainCategories.add(mainCategory);
                }
            } catch (JSONException e) {
                log.error("No such key \"name\" in json object " + mainCategory.toString());
            }
        }

        List<JSONObject> childCategoriesList = new ArrayList<JSONObject>();
        grabChildCategories(someMainCategories, childCategoriesList);
        return childCategoriesList;

    }

    public List<JSONObject> grabChildCategoriesToDB(String... someMainCategoriesNames) {
        List<JSONObject> childCategoriesList = grabChildCategories(someMainCategoriesNames);
        processEntityList(childCategoriesList);
        return childCategoriesList;
    }

    public List<JSONObject> grabAllChildCategories() {
        List<JSONObject> mainCategoriesList = grabMainCategories();
        List<JSONObject> allChildCategoriesList = new ArrayList<JSONObject>();
        grabChildCategories(mainCategoriesList, allChildCategoriesList);
        return allChildCategoriesList;
    }

    public List<JSONObject> grabAllChildCategoriesToDB(){
        List<JSONObject> allChildCategoriesList = grabAllChildCategories();
        processEntityList(allChildCategoriesList);
        return allChildCategoriesList;
    }
}
