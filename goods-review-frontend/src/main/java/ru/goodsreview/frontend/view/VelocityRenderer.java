package ru.goodsreview.frontend.view;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         11.10.12
 */
public class VelocityRenderer {
    private final static Logger log = Logger.getLogger(VelocityRenderer.class);

    private final static VelocityEngine VE = new VelocityEngine();

    static {
        try {
            VE.init();
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String render(final String templatePath, Map<String, Object> data) {
        try {
            final Template t = VE.getTemplate(templatePath, "UTF-8");
            final VelocityContext context = new VelocityContext();
            for (final Map.Entry<String, Object> entry : data.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
            final StringWriter writer = new StringWriter();
            t.merge(context, writer);
            return writer.toString();
        } catch (final Exception e) {
            log.error(String.format("error while rendering template %s with data %s", templatePath, data), e);
            throw new RuntimeException(e);
        }
    }
}
