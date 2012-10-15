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
public class MainPageView implements View<List<JSONObject>> {
    private final static String TEMPLATE_PATH = "goods-review-frontend/src/main/html/mainPage.vm";

    public String createPage(final List<JSONObject> data) {
        return new ViewHelper().createPage(TEMPLATE_PATH, data);
    }
}
