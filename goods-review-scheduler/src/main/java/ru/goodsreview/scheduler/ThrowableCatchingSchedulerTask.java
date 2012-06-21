package ru.goodsreview.scheduler;

import ru.goodsreview.scheduler.context.Context;

/**
 * User: daddy-bear
 * Date: 21.06.12
 * Time: 20:44
 */
class ThrowableCatchingSchedulerTask implements SchedulerTask {

    private final SchedulerTask innerTask;

    public ThrowableCatchingSchedulerTask(final SchedulerTask innerTask) {
        this.innerTask = innerTask;
    }

    @Override
    public TaskResult run(final Context context) {

        try {
            return innerTask.run(context);
        } catch (Throwable t) {
            return TaskResult.bad(t);
        }

    }
}
