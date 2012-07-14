package ru.goodsreview.core.model;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 17:06
 */
public interface Category {

    int getModelsNum();

    int getOffersNum();

    int getChildrenCount();

    String getName();

    long getParentId();

    long getId();

}
