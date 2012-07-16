package ru.goodsreview.api.grabber;

import org.json.JSONObject;
import ru.goodsreview.api.provider.ContentApiProvider;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.UrlRequest;

import java.util.HashMap;
import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public class CategoryGrabber {
    private final ContentApiProvider contentApiProvider;

    private enum JSONKeys {

        CATEGORIES("categories"),
        ITEMS("items");

        private final String key;

        JSONKeys(String key){
            this.key = key;
        }

        public String getKey(){
            return this.key;
        }
    }

    public CategoryGrabber(ContentApiProvider contentApiProvider){
        this.contentApiProvider = contentApiProvider;
    }

    public List<JSONObject> grabMainCategoriesList(){
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
        return contentApiProvider.provideAsList(urlRequest, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());
    }

//    public List<JSONObject> grabChildCategoriesList(){
//
//    }
}
