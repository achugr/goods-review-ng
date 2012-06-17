package ru.goodsreview.scheduler.context;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:02
 */
public abstract class AbstractContext implements Context {

    @Override
    public final int getParamAsInt(final String paramName) {
        return Integer.parseInt(getParam(paramName));
    }

    @Override
    public final int getParamAsInt(final String paramName, final int defaultValue) {
        try {
            return getParamAsInt(paramName);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    public final long getParamAsLong(final String paramName) {
        return Long.parseLong(getParam(paramName));
    }

    @Override
    public final long getParamAsLong(final String paramName, final long defaultValue) {
        try {
            return getParamAsLong(paramName);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}