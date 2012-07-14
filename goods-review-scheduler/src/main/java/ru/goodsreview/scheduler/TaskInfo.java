package ru.goodsreview.scheduler;

import ru.goodsreview.scheduler.context.Context;

/**
 * User: daddy-bear
 * Date: 21.06.12
 * Time: 20:52
 */
public class TaskInfo {

    private final long id;
    private final String beanName;
    private final Context context;

    public TaskInfo(final long id, final String beanName, final Context context) {
        this.id = id;
        this.beanName = beanName;
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public String getBeanName() {
        return beanName;
    }

    public Context getContext() {
        return context;
    }
}
