package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.RequestParams;
import ru.goodsreview.api.request.builder.UrlRequest;
import ru.goodsreview.core.db.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
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
        log.info("Grabbing main categories ended");
        return contentApiProvider.provideAsList(urlRequest, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());
    }

    public List<JSONObject> grabMainCategoriesToDB(){
        log.info("Grabbing main categories to DB started");
        List<JSONObject> mainCategoriesList = grabMainCategories();
        processEntityList(mainCategoriesList);
        log.info("Grabbing main categories to DB ended");
        return mainCategoriesList;
    }

    private void grabChildCategories(List<JSONObject> parentCategoriesList, List<JSONObject> allChildCategoriesList){
        try {
            for(JSONObject category : parentCategoriesList){
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

                        // Get child categories of the current category and add them to all child categories list
                        List<JSONObject> childCategoriesList = getContentApiProvider().provideAsList(urlRequest, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());
                        allChildCategoriesList.addAll(childCategoriesList);

                        // Going on grabbing child categories of the next level
                        grabChildCategories(childCategoriesList, allChildCategoriesList);
                    }
                }
            }
        } catch (JSONException e) {
            log.error("some problems with json", e);
            throw new RuntimeException();
        }
    }

    public List<JSONObject> grabChildCategories() {
        List<JSONObject> mainCategoriesList = grabMainCategories();
        List<JSONObject> allChildCategoriesList = new ArrayList<JSONObject>();
        grabChildCategories(mainCategoriesList, allChildCategoriesList);
        return allChildCategoriesList;
    }

    public List<JSONObject> grabChildCategoriesToDB(){
        List<JSONObject> allChildCategoriesList = grabChildCategories();
        processEntityList(allChildCategoriesList);
        return allChildCategoriesList;
    }
}
