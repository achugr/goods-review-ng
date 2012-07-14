package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:09
 */
public class TaskResult {
    private final static Logger log = Logger.getLogger(TaskResult.class);
    
    protected enum Status {
        OK,
        BAD
    }

    private final String message;
    private final Status status;

    String getMessage() {
        return message;
    }

    Status getStatus() {
        return status;
    }

    private TaskResult(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public static TaskResult ok(final String message) {
        return new TaskResult(message, Status.OK);
    }

    private static final String DEFAULT_MESSAGE = "Automatically generated message";

    public static TaskResult ok() {
        return ok(DEFAULT_MESSAGE);
    }

    public static TaskResult bad(final String message) {
        return new TaskResult(message, Status.BAD);
    }

    public static TaskResult bad(final Throwable throwable) {
        log.error("error while task executing", throwable);
        return bad(throwable.getMessage());
    }
}
