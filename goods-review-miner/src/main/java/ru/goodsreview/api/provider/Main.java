package ru.goodsreview.api.provider;

import org.json.JSONObject;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.ModelRequestBuilder;
import ru.goodsreview.api.request.builder.OpinionRequestBuilder;

import java.util.HashMap;
import java.util.List;

/**
 * Artemij Chugreev
 * Date: 16.06.12
 * Time: 20:45
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class Main {

    public static void main(String[] args) {
//        provideMethodUsageExample();
//         provideAsArrayUsageMethodExample();
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
        ContentAPIProvider contentAPIProvider = new ContentAPIProvider();
        System.out.println(contentAPIProvider.provide(urlRequest).toString());
    }

    private static void provideAsArrayUsageMethodExample(){
        ContentAPIProvider contentAPIProvider = new ContentAPIProvider();
        OpinionRequestBuilder opinionRequestBuilder = new OpinionRequestBuilder();
        UrlRequest urlRequest = opinionRequestBuilder.requestForOpinionOnModelById(7823691, new HashMap<String, String>());
        List<JSONObject> objectList = contentAPIProvider.provideAsArray(urlRequest, new String[]{"modelOpinions"}, "opinion");
        for(JSONObject object : objectList){
            System.out.println(object.toString());
        }
    }

    private static void provideMethodUsageExample() {
        ModelRequestBuilder modelRequestBuilder = new ModelRequestBuilder();
        for (int i = 0; i < 2; i++) {
            ContentAPIProvider contentAPIProvider = new ContentAPIProvider();
            System.out.println(modelRequestBuilder.requestForModelById(8281343, new HashMap<String, String>()));
            System.out.println(contentAPIProvider.provide(modelRequestBuilder.requestForModelById(8281343, new HashMap<String, String>())).toString());
        }
    }
}
