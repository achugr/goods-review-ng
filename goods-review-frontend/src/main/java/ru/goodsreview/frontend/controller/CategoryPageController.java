package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import ru.goodsreview.core.util.Pair;
import ru.goodsreview.frontend.model.CategoryPageModel;
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
public class CategoryPageController {
    public String generatePage(final long categoryId, final int pageNumber){
        final CategoryPageModel categoryPageModel = new CategoryPageModel();
        Pair<Integer, List<JSONObject>> categoryData = categoryPageModel.getModelsByCategoryId(categoryId, pageNumber);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("pagesNumber", categoryData.first);
        data.put("models", categoryData.second);
        return new CategoryPageView().createPage(data);
    }
}
