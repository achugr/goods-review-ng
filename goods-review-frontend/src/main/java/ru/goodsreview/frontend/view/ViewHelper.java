package ru.goodsreview.frontend.view;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
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

    public String createPage(final String templatePath, Map<String, Object> data) {
        VelocityEngine ve = new VelocityEngine();
        try {
            ve.init();
            Template t = ve.getTemplate(templatePath, "UTF-8");
            VelocityContext context = new VelocityContext();
            for(Map.Entry<String, Object> entry : data.entrySet()){
                context.put(entry.getKey(), entry.getValue());
            }
            StringWriter writer = new StringWriter();
            t.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            return "404 -> page not found";
        }
    }


}
