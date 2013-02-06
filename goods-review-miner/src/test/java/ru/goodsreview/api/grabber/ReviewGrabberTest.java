package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/grabbers.xml")
public class ReviewGrabberTest {
    public static final Logger log = Logger.getLogger(ReviewGrabber.class);

    @Autowired
    ReviewGrabber reviewGrabber;

    @Test
    public void grabReviewsTest(){
        reviewGrabber.grabAllReviews();
    }

    @Test
    public void grabReviewsForModelsFromDBTest(){
        reviewGrabber.grabReviewsForModelsFromDB();
    }

    @Test
    public void grabReviewsForSpecificCategoriesTest(){
        reviewGrabber.grabReviews("Ноутбуки");
    }
}
