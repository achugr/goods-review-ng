package ru.goodsreview.frontend.view.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         17.11.12
 */
public class JadeRenderer {

    private final static Logger log = Logger.getLogger(JadeRenderer.class);

    private final static JadeConfiguration config = new JadeConfiguration();

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
