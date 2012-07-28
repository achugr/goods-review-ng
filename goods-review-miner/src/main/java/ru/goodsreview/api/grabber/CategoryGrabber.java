package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.api.provider.ContentAPIProvider;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.RequestParams;
import ru.goodsreview.api.request.builder.UrlRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public class CategoryGrabber {
    private final static Logger log = Logger.getLogger(CategoryGrabber.class);
    private final ContentAPIProvider contentApiProvider;
    private static final Integer COUNT_MAX_VALUE = 30;

    private enum JSONKeys {

        CATEGORIES("categories"),
        ID("id"),
        ITEMS("items"),
        CHILDREN_COUNT("childrenCount");

        private final String key;

        JSONKeys(String key){
            this.key = key;
        }

        public String getKey(){
            return this.key;
        }
    }

    public CategoryGrabber(ContentAPIProvider contentApiProvider){
        this.contentApiProvider = contentApiProvider;
    }

    public List<JSONObject> grabMainCategoriesList(){
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        Map<String,String> parameters = new HashMap<String, String>();
        parameters.put(RequestParams.COUNT.getKey(), COUNT_MAX_VALUE.toString());
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(parameters);
        return contentApiProvider.provideAsList(urlRequest, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());
    }

    private void grabChildCategoriesList(List<JSONObject> parentCategoriesList, List<JSONObject> allChildCategoriesList){
        try {
            for(JSONObject category : parentCategoriesList){
                int childrenCount = category.getInt(JSONKeys.CHILDREN_COUNT.getKey());
                if(childrenCount != 0){
                    // Using category's id to construct request for it's child categories
                    long currId = category.getLong(JSONKeys.ID.getKey());
                    CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
                    UrlRequest urlRequest = categoryRequestBuilder.requestForListOfChildrenCategoriesById(currId, new HashMap<String, String>());

                    // Get child categories of the current category and add them to all child categories list
                    List<JSONObject> childCategoriesList = contentApiProvider.provideAsList(urlRequest,JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());
                    allChildCategoriesList.addAll(childCategoriesList);

                    // Going on grabbing child categories of the next level
                    grabChildCategoriesList(childCategoriesList, allChildCategoriesList);
                }
            }
        } catch (JSONException e) {
            //log.error("some problems with json", e);
            throw new RuntimeException();
        }
    }

    public List<JSONObject> grabChildCategoriesList(){
        List<JSONObject> mainCategoriesList = grabMainCategoriesList();
        List<JSONObject> allChildCategoriesList = new ArrayList<JSONObject>();
        grabChildCategoriesList(mainCategoriesList, allChildCategoriesList);
        return allChildCategoriesList;
    }
}
