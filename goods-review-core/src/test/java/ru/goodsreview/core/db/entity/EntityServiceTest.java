package ru.goodsreview.core.db.entity;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: daddy-bear
 * Date: 18.06.12
 * Time: 18:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ru/goodsreview/core/entity-service-test-config.xml")
public class EntityServiceTest {

    @Autowired
    private EntityService entityService;

}
