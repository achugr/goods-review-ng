package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import ru.goodsreview.frontend.model.CategoryModel;
import ru.goodsreview.frontend.view.velocity.SimpleViewBuilder;
import ru.goodsreview.frontend.view.velocity.TemplatePathsHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: timur
 * Date: 18.10.12
 * Time: 15:42
 */
public class CategoryController {
    private final SimpleViewBuilder viewBuilder =  new SimpleViewBuilder(TemplatePathsHolder.getCategoryTemplatePath());

    private final CategoryModel categoryPageModel = new CategoryModel();

    public String generatePage(final long categoryId, final int pageNumber, final int modelsOnPage){
        int pagesNumber = categoryPageModel.getModelsCount(categoryId);
        List<JSONObject> models = categoryPageModel.getModels(categoryId, pageNumber, modelsOnPage);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("pagesNumbers", pagesNumber);
        data.put("models", models);
        return viewBuilder.build(data);
    }
}
