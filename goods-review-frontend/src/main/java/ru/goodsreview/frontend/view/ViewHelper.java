package ru.goodsreview.frontend.view;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.json.JSONObject;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         11.10.12
 */
//TODO make precompiled templates for header, footer
public class ViewHelper {
    private static VelocityEngine ve = new VelocityEngine();
    static {
        try {
            ve.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        ve.setProperty(RuntimeConstants.RUNTIME_LOG, "frontend.log");
    }

    public static String createPage(final String templatePath, Map<String, Object> data) {

        try {
            Template t = ve.getTemplate(templatePath, "UTF-8");
            VelocityContext context = new VelocityContext();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
            StringWriter writer = new StringWriter();
            t.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
