package ru.goodsreview.core.util;

import java.util.Iterator;

/**
 * User: daddy-bear
 * Date: 13.07.12
 * Time: 20:26
 */
public class IteratorWrapper<T> implements Iterable<T> {

    private final Iterator<T> iterator;

    public IteratorWrapper(final Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }
}
