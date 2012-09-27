package ru.goodsreview.core.db.visitor;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:42
 */
public interface Visitor<T> {

    void visit(T t);

}
