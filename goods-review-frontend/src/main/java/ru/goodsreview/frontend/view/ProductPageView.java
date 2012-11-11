package ru.goodsreview.frontend.view;

import org.json.JSONObject;
import ru.goodsreview.core.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class ProductPageView extends SimpleView {
    private static final String TEMPLATE_PATH = "goods-review-frontend/src/main/html/productPageV2.vm";

    @Override
    protected String getPath() {
        return TEMPLATE_PATH;
    }
}
