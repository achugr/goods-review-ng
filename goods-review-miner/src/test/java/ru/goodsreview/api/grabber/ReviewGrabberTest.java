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
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 01.09.12
 * Time: 3:49
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/grabber-test-bean.xml")
public class ReviewGrabberTest {
    public static final Logger log = Logger.getLogger(ReviewGrabber.class);

    @Autowired
    ReviewGrabber reviewGrabber;

    @Test
    public void grabReviewsTest(){
        List<JSONObject> reviews = reviewGrabber.grabAllReviews();
        for(JSONObject review : reviews){
            log.debug(review.toString());
        }
    }

    @Test
    public void grabReviewsForModelsFromDBTest(){
        List<JSONObject> reviews = reviewGrabber.grabReviewsForModelsFromDB();
        for(JSONObject review : reviews){
            log.debug(review.toString());
        }
    }

    @Test
    public void grabReviewsForSpecificCategoriesTest(){
        List<JSONObject> specificReviews = reviewGrabber.grabReviews("Планшеты");
        for(JSONObject review : specificReviews){
            log.debug(review.toString());
        }
    }
}
