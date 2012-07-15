package ru.goodsreview.core.model;

import java.util.List;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 15:09
 */
public interface Model {

    long getId();

    long getCategoryId();

    List<String> getPhotos();

    String getMainPhoto();

    String getLink();

    String getName();

    String getDescription();

    boolean isGroup();

    boolean isNew();

    int getReviewsCount();

    double getRating();

    String getVendor();

    long getVendorId();



}
