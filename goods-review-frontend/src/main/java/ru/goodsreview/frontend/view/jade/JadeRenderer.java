package ru.goodsreview.frontend.view.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         17.11.12
 */
public class JadeRenderer {

    private final static Logger log = Logger.getLogger(JadeRenderer.class);

    private final static JadeConfiguration config = new JadeConfiguration();

    static {
//        TODO path hardcode
//        TemplateLoader loader = new FileTemplateLoader("/goods-review-frontend/src/main/static/jade", "UTF-8");
//        config.setTemplateLoader(loader);
    }

    public static String render(final String templatePath, Map<String, Object> data) {
        try {
            final JadeTemplate t = config.getTemplate(templatePath);
            return config.renderTemplate(t, data);
        } catch (final Exception e) {
            log.error(String.format("error while rendering template %s with data %s", templatePath, data), e);
            throw new RuntimeException(e);
        }
    }
}
