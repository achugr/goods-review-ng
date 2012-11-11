package ru.goodsreview.frontend.view;

import java.util.Map;

/**
 * @author Dmitry Batkovich <daddy-bear@yandex-team.ru>
 */
public abstract class SimpleView implements View<Map<String, Object>> {
    @Override
    public String createPage(final Map<String, Object> data) {
        return ViewHelper.createPage(getPath(), data);
    }

    protected abstract String getPath();
}
