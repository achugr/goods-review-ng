package ru.goodsreview.frontend.controller;

import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.frontend.model.FeatureForView;
import ru.goodsreview.frontend.model.ProductModel;
import ru.goodsreview.frontend.view.jade.JadeViewBuilder;
import ru.goodsreview.frontend.view.jade.TemplatePathsHolder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class ProductController {
    private final JadeViewBuilder viewBuilder = new JadeViewBuilder(TemplatePathsHolder.getModelPageTemplate());

    private final ProductModel productPageModel = new ProductModel();

    public String generatePage(final long modelId) {
        JSONObject model = productPageModel.getModel(modelId);
        List<JSONObject> reviews = productPageModel.getReviews(modelId);
        List<FeatureForView> featureForViewList = productPageModel.getInfoForJade(modelId);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("model", model);
        data.put("reviews", reviews);
        data.put("featureForViewList", featureForViewList);
        return viewBuilder.build(data);
    }

}
