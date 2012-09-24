package ru.goodsreview.core.db.entity;


import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.goodsreview.core.db.visitor.Visitor;

import java.util.Collections;

/**
 * User: daddy-bear
 * Date: 18.06.12
 * Time: 18:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:entity-service-test-config.xml")
public class EntityServiceTest {

    @Autowired
    private EntityService entityService;

    @Test
    public void testIt() throws Exception {
        JSONObject object = new JSONObject("{\"id\":\"3\",\"typeId\":\"4\",\"key\":\"va1lue\"}");
        entityService.writeEntities(Collections.singletonList(object));

        entityService.visitEntities(2, new Visitor<JSONObject>() {
            @Override
            public void visit(JSONObject jsonObject) {
                System.out.println(jsonObject);
            }
        });
    }

}
