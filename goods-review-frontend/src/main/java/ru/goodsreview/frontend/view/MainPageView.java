package ru.goodsreview.frontend.view;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         07.10.12
 */
public class MainPageView {
    private final static String TEMPLATE_PATH = "goods-review-frontend/src/main/html/mainPage.vm";

    public String createPage(final List<JSONObject> data) {
        VelocityEngine ve = new VelocityEngine();
        try {
            ve.init();
            Template t = ve.getTemplate(TEMPLATE_PATH, "UTF-8");
            VelocityContext context = new VelocityContext();
            context.put("data", data);
            StringWriter writer = new StringWriter();
            t.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            return new File("").getAbsolutePath();
        }
    }
}
