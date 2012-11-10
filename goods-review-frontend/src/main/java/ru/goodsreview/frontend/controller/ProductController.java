package ru.goodsreview.frontend.controller;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.frontend.model.ProductModel;
import ru.goodsreview.frontend.view.ProductPageView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class ProductController {
    private static final Logger log = Logger.getLogger(ProductController.class);

    public String generatePage(final long modelId) {
        final ProductModel productPageModel = new ProductModel();
        JSONObject model = productPageModel.getModelById(modelId);
        List<JSONObject> reviews = productPageModel.getReviewsByModelId(modelId);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("modelInfo", model);
        data.put("reviews", reviews);
        return new ProductPageView().createPage(data);
    }

}
