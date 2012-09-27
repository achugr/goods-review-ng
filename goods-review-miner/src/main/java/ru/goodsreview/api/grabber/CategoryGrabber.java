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

    public List<JSONObject> grabAllCategories() {
        List<JSONObject> mainCategories = grabMainCategories();
        List<JSONObject> allCategories = new ArrayList<JSONObject>(mainCategories);

        log.info("Grabbing child categories started");
        for(JSONObject mainCategory : mainCategories){
            grabChildCategories(mainCategory, allCategories);
        }
        log.info("Grabbing child categories ended");

        processEntityList(allCategories);
        return allCategories;
    }

    public List<JSONObject> grabCategories(String... specificMainCategoriesNames) {
        List<JSONObject> allMainCategories = grabMainCategories();
        List<JSONObject> specificCategories = new ArrayList<JSONObject>();

        log.info("Grabbing child categories started");
        grabFilteredChildCategories(allMainCategories, specificCategories, specificMainCategoriesNames);
        log.info("Grabbing child categories ended");

        processEntityList(specificCategories);
        return specificCategories;
    }

    private List<JSONObject> getNextLevelChildCategories(JSONObject parentCategory){
        List<JSONObject> childCategories = new ArrayList<JSONObject>();
        try {
            //Here can be thrown JSONException - if there are no such keys in json object "category"
            int childrenCount = parentCategory.getInt(JSONKeys.CHILDREN_COUNT.getKey());
            if(childrenCount != 0){

                int pageCount = (childrenCount / COUNT_MAX_VALUE) + 1;

                for(Integer pageNum = 1; pageNum <= pageCount; pageNum++){

                    // Using category's id to construct request for it's child categories
                    long currId = parentCategory.getLong(JSONKeys.ID.getKey());

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
                        childCategories.addAll(JSONUtil.extractList(mainObject, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey()));
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
            log.error("Error while parsing json object: no such keys", e);
        }
        return childCategories;
    }

    private void grabChildCategories(JSONObject parentCategory, List<JSONObject> allChildCategories){
        List<JSONObject> childCategories = getNextLevelChildCategories(parentCategory);
        allChildCategories.addAll(childCategories);
        //Going on grabbing child categories of the next level
        for(JSONObject childCategory : childCategories){
            grabChildCategories(childCategory, allChildCategories);
        }

    }

    private void grabFilteredChildCategories(List<JSONObject> categories, List<JSONObject> filteredCategories, String... specificCategoriesNames){
        List<String> specificCategoriesNamesList = Arrays.asList(specificCategoriesNames);
        for(JSONObject category : categories){
            try {
                if(specificCategoriesNamesList.contains(category.getString("name"))){
                    filteredCategories.add(category);
                    grabChildCategories(category, filteredCategories);
                }else{
                    List<JSONObject> yetNotFilteredCategories = getNextLevelChildCategories(category);
                    grabFilteredChildCategories(yetNotFilteredCategories, filteredCategories, specificCategoriesNames);
                }
            } catch (JSONException e) {
                log.error("No such key \"name\" in json object " + category.toString());
            }
        }
    }
}
