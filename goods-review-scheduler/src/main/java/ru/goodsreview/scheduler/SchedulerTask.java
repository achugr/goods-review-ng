package ru.goodsreview.scheduler;

import ru.goodsreview.scheduler.context.Context;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:09
 *
 * Any throwable allowed
 */
public interface SchedulerTask {

    TaskResult run(final Context context);

}
