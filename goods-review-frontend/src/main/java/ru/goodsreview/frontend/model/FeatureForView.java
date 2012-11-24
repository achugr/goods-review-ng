package ru.goodsreview.frontend.model;

import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         23.11.12
 */
public final class FeatureForView {
    private final String value;
    private final List<OpinionForView> opinions;

    public FeatureForView(String value, List<OpinionForView> opinions) {
        this.value = value;
        this.opinions = opinions;
    }

    public String getValue() {
        return value;
    }

    public List<OpinionForView> getOpinions() {
        return opinions;
    }
}
