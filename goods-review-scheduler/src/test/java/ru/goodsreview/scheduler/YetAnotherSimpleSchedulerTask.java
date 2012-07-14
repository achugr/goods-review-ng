package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import ru.goodsreview.scheduler.context.Context;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 13:49
 */
public class YetAnotherSimpleSchedulerTask implements SchedulerTask {
    private final static Logger log = Logger.getLogger(SimpleSchedulerTask.class);

    @Override
    public TaskResult run(final Context context) {
        try {
            log.info("one");
            Thread.sleep(30000);
            log.info("two");
            Thread.sleep(30000);
            log.info("three");
            Thread.sleep(30000);

        } catch (InterruptedException e) {
            return TaskResult.bad(e);
        }
        return TaskResult.bad("Oouuuch");
    }
}
