package ru.goodsreview.frontend.view.velocity;

import ru.goodsreview.frontend.view.ViewBuilder;

import java.util.Map;

/**
 * @author Dmitry Batkovich <daddy-bear@yandex-team.ru>
 */
public class SimpleViewBuilder implements ViewBuilder<Map<String, Object>> {

    private String templatePath;

    public SimpleViewBuilder(final String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public String build(final Map<String, Object> data) {
        return VelocityRenderer.render(templatePath, data);
    }
}
