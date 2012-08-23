package ru.goodsreview.analyzer.util.dictionary;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         24.08.12
 */
public interface KeyValueDictionary<E, T> extends Dictionary<E> {

    public T getValue(E key);
}
