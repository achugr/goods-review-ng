package ru.goodsreview.frontend.view.jade;

import ru.goodsreview.frontend.view.ViewBuilder;

import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         17.11.12
 */
public class JadeViewBuilder implements ViewBuilder<Map<String, Object>> {

    private String templatePath;

    public JadeViewBuilder(final String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public String build(Map<String, Object> data) {
        return JadeRenderer.render(templatePath, data);
    }
}
