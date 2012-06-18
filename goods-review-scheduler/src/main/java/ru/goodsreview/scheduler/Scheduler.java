package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:58
 */
public class Scheduler implements InitializingBean, ApplicationContextAware {
    private final static Logger log = Logger.getLogger(Scheduler.class);

    private JdbcTemplate jdbcTemplate;
    private int threadsCount;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Required
    public void setThreadsCount(final int threadsCount) {
        this.threadsCount = threadsCount;
    }

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Scheduler initializing...");
        new Thread(new Runnable() {

            private final ExecutorService executorService = Executors.newFixedThreadPool(Scheduler.this.threadsCount);

            @Override
            public void run() {

                try {
                    while (true) {


                        //jdbcTemplate.getJdbcOperations().query("SELECT * ");

                        Thread.sleep(30000); // = 1/2 of minute
                        log.info("heartbeat");
                    }
                } catch (InterruptedException e) {
                    log.error("", e);
                }

            }
        }).start();
        log.info("Scheduler started.");
    }
}

