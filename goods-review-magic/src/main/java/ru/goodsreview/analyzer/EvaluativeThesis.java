package ru.goodsreview.analyzer;

import ru.goodsreview.core.model.Thesis;

/**
 * Date: 01.10.12
 * Ilya Makeev
 */
public class EvaluativeThesis implements Thesis {
    private String value;
    private double importace = 0;
    private double sentiment = 0;

    EvaluativeThesis(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public double getImportance() {
        return importace;
    }

    @Override
    public double getSentiment() {
        return sentiment;
    }
}
