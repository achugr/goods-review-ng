package ru.goodsreview.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 14:19
 */
public abstract class Function<X, Y> {

    protected abstract Y apply(final X element);

    public List<Y> map(final Collection<X> elements) {
        final List<Y> result = new ArrayList<Y>();
        for (final X x: elements) {
            result.add(apply(x));
        }
        return result;
    }

}
