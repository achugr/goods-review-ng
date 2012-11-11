package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import ru.goodsreview.frontend.model.ProductModel;
import ru.goodsreview.frontend.view.SimpleViewBuilder;
import ru.goodsreview.frontend.view.TemplatePathsHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru -- лох
 *         06.10.12
 */
public class ProductController {
    private final SimpleViewBuilder viewBuilder = new SimpleViewBuilder(TemplatePathsHolder.getProductTemplatePath());

    private final ProductModel productPageModel = new ProductModel();

    public String generatePage(final long modelId) {
        JSONObject model = productPageModel.getModel(modelId);
        List<JSONObject> reviews = productPageModel.getReviews(modelId);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("modelInfo", model);
        data.put("reviews", reviews);
        return viewBuilder.build(data);
    }

}
