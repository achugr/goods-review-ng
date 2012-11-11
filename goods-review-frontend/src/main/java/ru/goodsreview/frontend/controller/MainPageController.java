package ru.goodsreview.frontend.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.frontend.model.MainPageModel;
import ru.goodsreview.frontend.view.SimpleViewBuilder;
import ru.goodsreview.frontend.view.TemplatePath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class MainPageController {
    private final SimpleViewBuilder viewBuilder = new SimpleViewBuilder(TemplatePath.MAIN_PAGE_TEMPLATE);

    private final MainPageModel mainPageModel = new MainPageModel();

    public String generatePage(){
        final List<JSONObject> models = mainPageModel.getPopularProducts(6);
        final Map<String, Object> data = new HashMap<String, Object>();
        data.put("models", models);
        return viewBuilder.build(data);
    }
}
