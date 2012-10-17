package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import ru.goodsreview.frontend.model.MainPageModel;
import ru.goodsreview.frontend.view.MainPageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class MainPageController {

    public String generatePage(){
        final List<JSONObject> models = new MainPageModel().getPopularProducts(6);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("models", models);
        return new MainPageView().createPage(data);
    }
}
