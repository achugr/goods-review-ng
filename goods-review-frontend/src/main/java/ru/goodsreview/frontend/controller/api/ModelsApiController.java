package ru.goodsreview.frontend.controller.api;

import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.frontend.model.MainPageModel;

import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         25.11.12
 */
public class ModelsApiController {
    private final MainPageModel mainPageModel = new MainPageModel();

    public String getPopularProducts(final int modelsOnPage){
        List<JSONObject> popularProducts =  mainPageModel.getPopularProducts(modelsOnPage);
        final JSONObject data = new JSONObject();
        try {
            data.put("models", popularProducts);
            data.put("modelsOnPage", modelsOnPage);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return data.toString();
    }
}
