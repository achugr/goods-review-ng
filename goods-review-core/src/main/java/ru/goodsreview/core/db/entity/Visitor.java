package ru.goodsreview.core.db.entity;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:42
 */
public interface Visitor<T> {

    void visit(T t);

}
