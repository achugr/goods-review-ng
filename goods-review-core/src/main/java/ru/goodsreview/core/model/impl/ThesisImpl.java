package ru.goodsreview.core.model.impl;

import ru.goodsreview.core.model.Thesis;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 17:19
 */
public class ThesisImpl implements Thesis {

    private final String value;
    private final double importance;
    private final double sentiment;

    public ThesisImpl(final String value, final double importance, final double sentiment) {
        this.value = value;
        this.importance = importance;
        this.sentiment = sentiment;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public double getImportance() {
        return importance;
    }

    @Override
    public double getSentiment() {
        return sentiment;
    }

    public static ThesisImpl of(final String value, final double importance, final double sentiment) {
        return new ThesisImpl(value, importance, sentiment);
    }
}
