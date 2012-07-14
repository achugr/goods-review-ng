package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.concurrent.*;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:58
 */
public class Scheduler implements InitializingBean, ApplicationContextAware {
    private final static Logger log = Logger.getLogger(Scheduler.class);

    private int threadsCount;
    private ApplicationContext applicationContext;
    private String schedulerName;
    private TimeTableService timeTableService;

    @Required
    public void setTimeTableService(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @Required
    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Required
    public void setThreadsCount(final int threadsCount) {
        this.threadsCount = threadsCount;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Scheduler initializing...");
        new Thread(new Runnable() {

            private final ExecutorService executorService = Executors.newFixedThreadPool(Scheduler.this.threadsCount);
            private final Map<Long, Future<TaskResult>> currentTasksFuture = new HashMap<Long, Future<TaskResult>>();

            @Override
            public void run() {

                try {
                    while (true) {

                        checkTasks();

                        final List<Long> newTaskIds = new ArrayList<Long>();
                        for (TaskInfo taskInfo : timeTableService.fetchNewTasks(schedulerName, currentTasksFuture.keySet())) {
                            executeTask(taskInfo);
                            newTaskIds.add(taskInfo.getId());
                        }

                        timeTableService.addNewRunningTask(newTaskIds);


                        Thread.sleep(10000); // 10 seconds
                        log.info("heartbeat");
                    }
                } catch (InterruptedException e) {
                    log.error("thread was interrupted", e);
                }

            }

            private void executeTask(final TaskInfo info) {
                final SchedulerTask task = (SchedulerTask) Scheduler.this.applicationContext.getBean(info.getBeanName());

                final Future<TaskResult> futureResult = executorService.submit(new Callable<TaskResult>() {

                    @Override
                    public TaskResult call() throws Exception {
                        Thread.currentThread().setName("SchedulerTask-" + info.getId());
                        return new ThrowableCatchingSchedulerTask(task).run(info.getContext());
                    }
                });

                currentTasksFuture.put(info.getId(), futureResult);
            }

            private void checkTasks() {
                log.info("check new tasks");
                final List<Long> finishedTasks = new LinkedList<Long>();
                final List<Long> forUpdatePingTime = new LinkedList<Long>();
                for (Map.Entry<Long, Future<TaskResult>> e : currentTasksFuture.entrySet()) {
                    if (e.getValue().isDone()) {
                        finishedTasks.add(e.getKey());
                        try {
                            timeTableService.addTaskResult(e.getValue().get(), e.getKey());
                        } catch (InterruptedException exception) {
                            log.error(exception.getMessage(), exception);
                        } catch (ExecutionException exception) {
                            log.error(exception.getMessage(), exception);
                        }
                    }
                    forUpdatePingTime.add(e.getKey());
                }

                timeTableService.updateLastPingTime(forUpdatePingTime);
                for (Long finishedTaskId : finishedTasks) {
                    currentTasksFuture.remove(finishedTaskId);
                }

            }

        }, "Scheduler").start();
        log.info("Scheduler started...");
    }
}

