package ru.goodsreview.scheduler.context;

import java.util.List;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:02
 */
public interface Context {

    int getParamAsInt(final String paramName);

    int getParamAsInt(final String paramName, final int defaultValue);

    String getParam(final String paramName);

    long getParamAsLong(final String paramName);

    long getParamAsLong(final String paramName, final long defaultValue);

    List<String> getMultiParam(final String paramName);

}