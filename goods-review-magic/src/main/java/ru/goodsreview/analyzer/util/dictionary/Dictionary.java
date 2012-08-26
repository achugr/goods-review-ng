package ru.goodsreview.analyzer.util.dictionary;

import java.util.Collection;

/**
 * achugr, achugr@yandex-team.ru
 * 21.07.12
 */
public interface Dictionary<E> {

    boolean contains(E key);

    public void print();

}
