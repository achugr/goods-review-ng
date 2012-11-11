package ru.goodsreview.frontend.view;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         11.10.12
 */
public interface ViewBuilder<T> {

    String build(T data);
}
