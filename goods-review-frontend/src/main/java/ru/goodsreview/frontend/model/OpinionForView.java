package ru.goodsreview.frontend.model;

import java.util.List;
import java.util.Map;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         23.11.12
 */
public final class OpinionForView {
    private final String value;
    private final double importance;
    private final double sentiment;
    private final List<String> sentences;

    public OpinionForView(String value, double importance, double sentiment, List<String> sentences) {
        this.value = value;
        this.importance = importance;
        this.sentiment = sentiment;
        this.sentences = sentences;
    }

    public String getValue() {
        return value;
    }

    public double getImportance() {
        return importance;
    }

    public double getSentiment() {
        return sentiment;
    }

    public List<String> getSentences() {
        return sentences;
    }
}

