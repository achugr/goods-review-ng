package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import ru.goodsreview.frontend.model.SearchPageModel;
import ru.goodsreview.frontend.view.jade.JadeViewBuilder;
import ru.goodsreview.frontend.view.jade.TemplatePathsHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:10
 */
public class SearchPageController {
    private final JadeViewBuilder viewBuilder = new JadeViewBuilder(TemplatePathsHolder.getSearchResultPageTemplate());

    private final SearchPageModel searchPageModel = new SearchPageModel();

    public String generatePage(String searchQuery) {
        final List<JSONObject> searchResults = searchPageModel.getSearchResults(searchQuery);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("searchQuery", searchQuery);
        data.put("models", searchResults);
        return viewBuilder.build(data);
    }
}
