package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import ru.goodsreview.scheduler.context.Context;

/**
 * User: daddy-bear
 * Date: 21.06.12
 * Time: 20:37
 */
public class SimpleSchedulerTask implements SchedulerTask {
    private final static Logger log = Logger.getLogger(SimpleSchedulerTask.class);

    @Override
    public TaskResult run(Context context) {

        log.info("tik-tak");

        return TaskResult.ok("rrrroar");
    }
}
