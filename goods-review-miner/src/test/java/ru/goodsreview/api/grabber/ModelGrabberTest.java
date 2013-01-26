package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/grabbers.xml")
public class ModelGrabberTest {
    public static final Logger log = Logger.getLogger(ModelGrabberTest.class);

    @Autowired
    private ModelGrabber modelGrabber;

    @Test
    public void grabModelsTest(){
        // Warning: This method may take a lot of time while executing
        List<JSONObject> models = modelGrabber.grabAllModels();
        for(JSONObject model : models){
            log.debug(model.toString());
        }
    }

    @Test
    public void grabModelsForCategoriesFromDBTest(){
        // Warning: This method may take a lot of time while executing
        List<JSONObject> models = modelGrabber.grabModelsForCategoriesFromDB();
        for(JSONObject model : models){
            log.debug(model.toString());
        }
    }

    @Test
    public void grabModelsForSpecificCategoriesTest(){
        List<JSONObject> specificModels = modelGrabber.grabModels("Фото");
        for(JSONObject model : specificModels){
            log.debug(model.toString());
        }
    }
}
