package ru.goodsreview.frontend.controller.api;

import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.frontend.model.CategoryModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         25.11.12
 */
public class CategoryApiController {

    private final CategoryModel categoryPageModel = new CategoryModel();

    public String getData(final long categoryId, final int pageNumber, final int modelsOnPage){
        List<JSONObject> models = categoryPageModel.getModels(categoryId, pageNumber, modelsOnPage);
        final JSONObject data = new JSONObject();
        try {
            data.put("page-number", pageNumber);
            data.put("models-on-page", modelsOnPage);
            data.put("models", models);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return data.toString();
    }
}
