package ru.goodsreview.frontend.controller.api;

import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.frontend.model.SearchPageModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         25.11.12
 */
public class SearchApiController {

    private final SearchPageModel searchPageModel = new SearchPageModel();

    public String getModelsByQuery(final String searchQuery, final int pageNumber, final int modelsOnPage) {
        final List<JSONObject> searchResults = searchPageModel.getSearchResults(searchQuery, pageNumber, modelsOnPage);
        JSONObject data = new JSONObject();
        try {
            data.put("search-query", searchQuery);
            data.put("models", searchResults);
            data.put("page-number", pageNumber);
            data.put("models-on-page", modelsOnPage);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return data.toString();
    }
}
