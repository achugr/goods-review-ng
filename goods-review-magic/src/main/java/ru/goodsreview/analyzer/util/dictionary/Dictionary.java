package ru.goodsreview.analyzer.util.dictionary;

import java.util.Collection;

/**
 * achugr, achugr@yandex-team.ru
 * 21.07.12
 *
 * //TODO wtf? generic? не?
 */
public interface Dictionary {

    public Object getDictionary();

    public boolean contains(Object key);

}
