package ru.goodsreview.frontend.view;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 19.10.12
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class CategoryPageView implements View<Map<String, Object>> {
    // TODO Create categoryPage.vm in goods-review-frontend/src/main/html/
    private static final String TEMPLATE_PATH = "goods-review-frontend/src/main/html/categoryPage.vm";

    @Override
    public String createPage(Map<String, Object> data) {
        return new ViewHelper().createPage(TEMPLATE_PATH, data);
    }
}
