package ru.goodsreview.api.provider;

import org.json.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import ru.goodsreview.api.url.generator.ModelRequestBuilder;
import ru.goodsreview.api.url.generator.OpinionRequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
         provideAsArrayUsageMethodExample();

    }

    private static void provideAsArrayUsageMethodExample(){
        ContentAPIProvider contentAPIProvider = new ContentAPIProvider();
        OpinionRequestBuilder opinionRequestBuilder = new OpinionRequestBuilder();
        UrlRequest urlRequest = opinionRequestBuilder.requestForOpinionOnModelById(7846162, new HashMap<String, String>());
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
