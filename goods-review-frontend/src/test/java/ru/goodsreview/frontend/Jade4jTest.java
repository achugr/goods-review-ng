package ru.goodsreview.frontend;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         16.11.12
 */
public class Jade4jTest {

    @Test
    public void testIt() throws IOException {
        JadeTemplate template = Jade4J.getTemplate("goods-review-frontend/src/main/static/jade/test.jade");

        System.out.println(new File(".").getAbsolutePath());
        Map<String, Object> model = new HashMap<String, Object>();
        List<String> models = new ArrayList<String>();
        models.add("model1");
        models.add("model2");
        models.add("model3");
        models.add("model4");
        models.add("model5");
        model.put("models", models);;
        String html = Jade4J.render(template, model);
        System.out.println(html);
    }
}