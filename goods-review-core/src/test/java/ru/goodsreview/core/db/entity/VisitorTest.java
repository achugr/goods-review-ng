package ru.goodsreview.core.db.entity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.goodsreview.core.db.visitor.CategoryHelper;
import ru.goodsreview.core.db.visitor.ReviewHelper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         21.09.12
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:visitor-test-config.xml")
public class VisitorTest {

    @Autowired
    private CategoryHelper categoryVisitor;
    @Autowired
    private ReviewHelper reviewVisitor;

    @Test
    public void categoryVisitorTest() {
        Collection<JSONObject> result = categoryVisitor.visit(91577);
        for (JSONObject jsonObject : result) {
            System.out.println(jsonObject.toString());
        }
    }

    @Test
    public void reviewVisitorTest() {
        Collection<JSONObject> result = reviewVisitor.visit(5081373);
        try {
            PrintWriter printWriter = new PrintWriter("reviews.txt");

            for (JSONObject reviewObject : result) {
                try {
                    final String review = reviewObject.getString("text");
                    printWriter.println(review);
                } catch (JSONException e) {
                }
            }
            printWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
