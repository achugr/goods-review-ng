package ru.goodsreview.api.grabber;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 27.08.12
 * Time: 4:31
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/grabber-test-bean.xml")
public class ModelGrabberTest {
    public static final Logger log = Logger.getLogger(ModelGrabberTest.class);

    @Autowired
    private ModelGrabber modelGrabber;

    @Test
    public void paramTest(){
        Assert.assertNotNull(modelGrabber.getContentApiProvider());
        Assert.assertNotNull(modelGrabber.getGrabberBatch());
    }

    @Test
    public void grabModelsTest(){
        List<JSONObject> modelsList = modelGrabber.grabModelsToDB();
    }
}