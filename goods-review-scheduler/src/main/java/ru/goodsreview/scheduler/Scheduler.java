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
                        for (TaskParameters taskParameters : timeTableService.fetchNewTasks(schedulerName, currentTasksFuture.keySet())) {
                            executeTask(taskParameters);
                            newTaskIds.add(taskParameters.getId());
                        }

                        timeTableService.addNewRunningTask(newTaskIds);


                        Thread.sleep(10000); // 10 seconds
                        log.info("heartbeat");
                    }
                } catch (InterruptedException e) {
                    log.error("thread was interrupted", e);
                }

            }

            private void executeTask(final TaskParameters parameters) {
                final SchedulerTask task = (SchedulerTask) Scheduler.this.applicationContext.getBean(parameters.getBeanName());

                final Future<TaskResult> futureResult = executorService.submit(new Callable<TaskResult>() {

                    @Override
                    public TaskResult call() throws Exception {
                        Thread.currentThread().setName("SchedulerTask-" + parameters.getId());
                        return new ThrowableCatchingSchedulerTask(task).run(parameters.getContext());
                    }
                });

                currentTasksFuture.put(parameters.getId(), futureResult);
            }

            private void checkTasks() {
                log.info("check new tasks");
                final List<Long> finishedTasks = new LinkedList<Long>();
                final List<Long> notFinishedTasks = new LinkedList<Long>();
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
                    } else {
                        notFinishedTasks.add(e.getKey());
                    }
                }
                for (Long finishedTaskId : finishedTasks) {
                    currentTasksFuture.remove(finishedTaskId);
                }

                timeTableService.updateLastPingTime(notFinishedTasks);
            }

        }, "Scheduler").start();
        log.info("Scheduler started...");
    }
}

