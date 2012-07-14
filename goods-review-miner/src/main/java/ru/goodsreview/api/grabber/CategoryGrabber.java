package ru.goodsreview.api.grabber;

import org.json.JSONObject;
import ru.goodsreview.api.provider.ContentAPIProvider;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.UrlRequest;

import java.util.HashMap;
import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public class CategoryGrabber {
    private final ContentAPIProvider contentAPIProvider;

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

    public CategoryGrabber(ContentAPIProvider contentAPIProvider){
        this.contentAPIProvider = contentAPIProvider;
    }

    public List<JSONObject> grabMainCategoriesList(){
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
        return contentAPIProvider.provideAsList(urlRequest, JSONKeys.ITEMS.getKey(), JSONKeys.CATEGORIES.getKey());
    }

//    public List<JSONObject> grabChildCategoriesList(){
//
//    }
}
