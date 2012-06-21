package ru.goodsreview.scheduler;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:08
 *
 * Do we really need to use this special exception?
 */
public class SchedulerException extends Exception {
    private static final long serialVersionUID = 7992791415956201631L;

    public SchedulerException() {
    }

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchedulerException(Throwable cause) {
        super(cause);
    }
}
