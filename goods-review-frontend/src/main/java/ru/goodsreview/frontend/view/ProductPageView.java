package ru.goodsreview.frontend.view;

import org.json.JSONObject;
import ru.goodsreview.core.util.Pair;

import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class ProductPageView implements View<Pair<JSONObject, List<JSONObject>>>{
    private static final String TEMPLATE_PATH = "goods-review-frontend/src/main/html/productPage.vm";

    @Override
    public String createPage(Pair<JSONObject, List<JSONObject>> data) {
        return new ViewHelper().createPage(TEMPLATE_PATH, data);
    }
}
