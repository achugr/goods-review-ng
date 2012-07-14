package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import ru.goodsreview.scheduler.context.Context;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 14:09
 */
public class SimpleSchedulerTaskWithParams implements SchedulerTask {
    private final static Logger log = Logger.getLogger(SimpleSchedulerTask.class);

    @Override
    public TaskResult run(final Context context) {


        log.info(context.getParam("key"));

        return TaskResult.ok("rrrroar");
    }
}
