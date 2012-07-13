package ru.goodsreview.scheduler;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:55
 */
public enum SchedulingType {
    MINUTE(60000L),
    HOUR(3600000L),
    DAY(86400000L),
    ONCE(-1L);

    private final long factor;

    private SchedulingType(final long factor) {
        this.factor = factor;
    }

    public long getAbsoluteInterval(final long interval) {
        return factor * interval;
    }

    public static SchedulingType getByName(String name) {
        for (SchedulingType type: values()) {
            if (type.toString().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid scheduling type with name: " + name);
    }

    public static long getAbsoluteInterval(final String typeName, final long interval) {
        return getByName(typeName).getAbsoluteInterval(interval);
    }
}
