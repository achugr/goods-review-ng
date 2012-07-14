package ru.goodsreview.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: daddy-bear
 * Date: 13.07.12
 * Time: 22:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:experiment.xml")
public class SchedulerExperiment {

    @Test
    public void testIt() throws Exception {
        Thread.sleep(Long.MAX_VALUE);
    }
}
