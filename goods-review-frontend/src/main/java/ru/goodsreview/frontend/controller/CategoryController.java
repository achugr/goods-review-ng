package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.frontend.model.CategoryModel;
import ru.goodsreview.frontend.view.SimpleViewBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: timur
 * Date: 18.10.12
 * Time: 15:42
 */
public class CategoryController {
    //"goods-review-frontend/src/main/html/categoryPage.vm"
    private SimpleViewBuilder viewBuilder;

    @Required
    public void setViewBuilder(final SimpleViewBuilder viewBuilder) {
        this.viewBuilder = viewBuilder;
    }

    public String generatePage(final long categoryId, final int pageNumber){
        final CategoryModel categoryPageModel = new CategoryModel();
        int pagesNumber = categoryPageModel.getModelsCount(categoryId);
        List<JSONObject> models = categoryPageModel.getModelsByCategoryId(categoryId, pageNumber);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("pagesNumbers", pagesNumber);
        data.put("models", models);
        return viewBuilder.build(data);
    }
}
