package ru.goodsreview.frontend.model;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         23.11.12
 */
public final class DataForViewUtils {
    private static final Logger log = Logger.getLogger(DataForViewUtils.class);

    private DataForViewUtils() {
    }

    public static List<FeatureForView> convertToObjectForView(final JSONObject jsonDataForView) {
        List<FeatureForView> featureForViewList = new LinkedList<FeatureForView>();
        try {
            JSONArray jsonFeatures = jsonDataForView.getJSONArray("features");
            for (int i = 0; i < jsonFeatures.length(); i++) {
                final JSONObject jsonFeature = jsonFeatures.getJSONObject(i);

                JSONArray jsonOpinions = jsonFeature.getJSONArray("opinions");
                final List<OpinionForView> opinionForViewList = new LinkedList<OpinionForView>();
                for (int j = 0; j < jsonOpinions.length(); j++) {
                    final JSONObject jsonOpinion = jsonOpinions.getJSONObject(j);
                    final JSONArray jsonSentences = jsonOpinion.getJSONArray("sentences");
                    final List<String> sentences = new LinkedList<String>();
                    for (int k = 0; k < jsonSentences.length(); k++) {
                        sentences.add(jsonSentences.getString(k));
                    }
                    final String opinionValue = jsonOpinion.getString("opinion");
                    final double importance = jsonOpinion.getDouble("importance");
                    final double sentiment = jsonOpinion.getDouble("sentiment");
                    final OpinionForView opinionForView = new OpinionForView(opinionValue, importance, sentiment, sentences);
                    opinionForViewList.add(opinionForView);
                }
                final String featureValue = jsonFeature.getString("feature");
                final int plusesNumber = jsonFeature.getInt("pluses-number");
                final int minusesNumber = jsonFeature.getInt("minuses-number");
                FeatureForView featureForView = new FeatureForView(featureValue, opinionForViewList, plusesNumber, minusesNumber);
                featureForViewList.add(featureForView);
            }
        } catch (JSONException e) {
            log.error("Some problems with json");
            throw new RuntimeException(e);
        }
        return featureForViewList;
    }
}
