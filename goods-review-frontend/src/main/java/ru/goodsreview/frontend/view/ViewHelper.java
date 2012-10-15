package ru.goodsreview.frontend.view;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         11.10.12
 */
//TODO make precompiled templates for header, footer
public class ViewHelper <T> {

    public String createPage(final String templatePath,  ) {
        VelocityEngine ve = new VelocityEngine();
        try {
            ve.init();
            Template t = ve.getTemplate(templatePath, "UTF-8");
            VelocityContext context = new VelocityContext();
            context.put("data", data);
            StringWriter writer = new StringWriter();
            t.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            return "404 -> page not found";
        }
    }


}
