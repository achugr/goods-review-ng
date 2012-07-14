package ru.goodsreview.core.model;

import java.util.Date;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 14:34
 */
public interface Review {

    String getPro();

    String getContra();

    String getText();

    int getGrade();

    int getAgree();

    int getReject();

    Date getDate();

    long getId();

    long getModelId();

    List<Thesis> getThesises();

    void addThesises(final List<Thesis> thesises);

}
