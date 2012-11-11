package ru.goodsreview.frontend.controller;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.frontend.model.ProductModel;
import ru.goodsreview.frontend.view.SimpleViewBuilder;
import ru.goodsreview.frontend.view.TemplatePath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru -- лох
 *         06.10.12
 */
public class ProductController {
    private final SimpleViewBuilder viewBuilder = new SimpleViewBuilder(TemplatePath.PRODUCT_PAGE_TEMPLATE);

    private final ProductModel productPageModel = new ProductModel();

    public String generatePage(final long modelId) {
        JSONObject model = productPageModel.getModelById(modelId);
        List<JSONObject> reviews = productPageModel.getReviewsByModelId(modelId);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("modelInfo", model);
        data.put("reviews", reviews);
        return viewBuilder.build(data);
    }

}
