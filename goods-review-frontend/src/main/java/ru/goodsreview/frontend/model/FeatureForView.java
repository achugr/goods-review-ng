package ru.goodsreview.frontend.model;

import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         23.11.12
 */
public final class FeatureForView {
    private final String value;
    private final int plusesNumber;
    private final int minusesNumber;
    private final List<OpinionForView> opinions;

    public FeatureForView(String value, List<OpinionForView> opinions, int plusesNumber, int minusesNumber) {
        this.value = value;
        this.opinions = opinions;
        this.plusesNumber = plusesNumber;
        this.minusesNumber = minusesNumber;
    }

    public String getValue() {
        return value;
    }

    public List<OpinionForView> getOpinions() {
        return opinions;
    }

    public int getPlusesNumber() {
        return plusesNumber;
    }

    public int getMinusesNumber() {
        return minusesNumber;
    }
}
