package ru.goodsreview.frontend.controller.api;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.frontend.model.ProductModel;

import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         30.11.12
 */
public class ModelApiController {
    private static final Logger log = Logger.getLogger(ModelApiController.class);

    private final ProductModel productModel = new ProductModel();

    public String getModelById(final long modelId){
        final JSONObject data = new JSONObject();
        try {
            final JSONObject features = productModel.getInfo(modelId);
            log.info(features);
            data.put("features", features.getJSONArray("features"));

            final JSONObject modelInfo = productModel.getInfo(modelId);
            data.put("model", modelInfo);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return data.toString();
    }

    public String getReviewsOnModel(final long modelId){
        final List<JSONObject> reviews = productModel.getReviews(modelId);
        return new JSONArray(reviews).toString();
    }
}
