package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import ru.goodsreview.frontend.model.CategoryModel;
import ru.goodsreview.frontend.view.CategoryPageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 18.10.12
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class CategoryController {
    public String generatePage(final long categoryId, final int pageNumber){
        final CategoryModel categoryPageModel = new CategoryModel();
        int pagesNumber = categoryPageModel.getModelsNumber(categoryId);
        List<JSONObject> models = categoryPageModel.getModelsByCategoryId(categoryId, pageNumber);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("pagesNumbers", pagesNumber);
        data.put("models", models);
        return new CategoryPageView().createPage(data);
    }
}
