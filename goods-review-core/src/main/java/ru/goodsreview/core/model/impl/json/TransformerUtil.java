package ru.goodsreview.core.model.impl.json;

import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.core.model.Category;
import ru.goodsreview.core.model.Model;
import ru.goodsreview.core.model.Review;
import ru.goodsreview.core.model.Thesis;

/**
 * User: daddy-bear
 * Date: 15.07.12
 * Time: 9:10
 */
public final class TransformerUtil {

    private TransformerUtil() {}

    public static JSONObject fromModel(final Model model) {
        return ((ModelOverJson) model).jsonObject;
    }

    public static JSONObject fromReview(final Review review) {
        return ((ReviewOverJson) review).jsonObject;
    }

    public static JSONObject fromThesis(final Thesis thesis) {
        if (thesis instanceof ThesisOverJson) {
            return ((ThesisOverJson) thesis).jsonObject;
        }
        try {
            final JSONObject rawThesis = new JSONObject();
            rawThesis.put(ThesisOverJson.VALUE_ATTR, thesis.getValue());
            rawThesis.put(ThesisOverJson.IMPORTANCE_ATTR, thesis.getImportance());
            rawThesis.put(ThesisOverJson.SENTIMENT_ATTR, thesis.getSentiment());

            return rawThesis;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject fromCategory(final Category category) {
        return ((CategoryOverJson) category).jsonObject;
    }

    public static JSONObject fromAnyModel(final Object anyOverJsonModel) {
        if (anyOverJsonModel instanceof Model) {
            return fromModel((Model) anyOverJsonModel);
        } else if (anyOverJsonModel instanceof Review) {
            return fromReview((Review) anyOverJsonModel);
        } else if (anyOverJsonModel instanceof Category) {
            return fromCategory((Category) anyOverJsonModel);
        } else if (anyOverJsonModel instanceof Thesis) {
            return fromThesis((Thesis) anyOverJsonModel);
        }
        throw new IllegalArgumentException();
    }
}
