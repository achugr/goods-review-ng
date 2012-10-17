package ru.goodsreview.frontend.view;

import org.json.JSONObject;
import ru.goodsreview.core.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class ProductPageView implements View<Map<String, Object>>{
    private static final String TEMPLATE_PATH = "goods-review-frontend/src/main/html/productPage.vm";

    @Override
    public String createPage(Map<String, Object> data) {
        return new ViewHelper().createPage(TEMPLATE_PATH, data);
    }
}
