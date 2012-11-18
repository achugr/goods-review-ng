package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.view.jade.JadeViewBuilder;
import ru.goodsreview.frontend.view.jade.TemplatePathsHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         17.11.12
 */
@Path("/jade")
public class JadeTestPageServlet {
    private final JadeViewBuilder jadeViewBuilder = new JadeViewBuilder(TemplatePathsHolder.getTestPageTemplate());

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getPage(){
        Map<String, Object> data = new HashMap<String, Object>();
        List<String> models = new ArrayList<String>();
        models.add("model1");
        models.add("model2");
        models.add("model3");
        models.add("model4");
        models.add("model5");
        data.put("models", models);
        return jadeViewBuilder.build(data);
    }

}
