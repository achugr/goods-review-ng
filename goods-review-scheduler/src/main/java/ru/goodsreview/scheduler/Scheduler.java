package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.concurrent.*;

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
            private final Map<Long, Future<TaskResult>> currentTasksFuture = new HashMap<Long, Future<TaskResult>>();

            @Override
            public void run() {

                try {
                    while (true) {

                        checkTasks();

                        //TODO
                        //try find new tasks
                        //dont execute task if it already running !!!


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
                        return new ThrowableCatchingSchedulerTask(task).run(parameters.getContext());
                    }
                });

                currentTasksFuture.put(parameters.getId(), futureResult);
            }

            private void checkTasks() {
                final List<Long> finishedTasks = new LinkedList<Long>();
                for (Map.Entry<Long, Future<TaskResult>> e : currentTasksFuture.entrySet()) {
                    if (e.getValue().isDone()) {
                        try {
                            taskResultDbController.addTaskResult(e.getValue().get());
                        } catch (InterruptedException ex) {
                            log.error(ex.getMessage(), ex);
                        } catch (ExecutionException ex) {
                            log.error(ex.getMessage(), ex);
                        }

                        finishedTasks.add(e.getKey());
                    } else {
                        //TODO. ..ну
                          DbControllerFactory.instance().taskDbController().updateLastPingTime(e.getKey());
                    }
                }

                for (Long finishedTaskId : finishedTasks) {
                    currentTasksFuture.remove(finishedTaskId);
                }
            }

        }).start();
        log.info("Scheduler started...");
    }
}

