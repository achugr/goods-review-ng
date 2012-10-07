package ru.goodsreview.frontend;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import java.io.StringWriter;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class Test {

    public static void main(String[] args)
            throws Exception {
        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        /*  next, get the Template  */
        Template t = ve.getTemplate("goods-review-frontend/src/main/html/mainPage.vm");
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("some", "entity");
        context.put("brand", "GoodsReview");
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        /* show the World */
        System.out.println(writer.toString());
    }
}
