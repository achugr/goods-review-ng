package ru.goodsreview.core.model.impl.json;

import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.core.model.Thesis;

import static ru.goodsreview.core.util.JSONUtil.unsafeGetString;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 17:32
 */
public class ThesisOverJson implements Thesis {

    private final JSONObject jsonObject;

    public ThesisOverJson(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String getValue() {
        return unsafeGetString(jsonObject, "value");
    }

    @Override
    public double getImportance() {
        return Double.parseDouble(unsafeGetString(jsonObject, "importance"));
    }

    @Override
    public double getSentiment() {
        return Double.parseDouble(unsafeGetString(jsonObject, "sentiment"));
    }

    protected static JSONObject toJsonObject(final Thesis thesis) {
        if (thesis instanceof ThesisOverJson) {
            return ((ThesisOverJson) thesis).jsonObject;
        }
        try {
            final JSONObject rawThesis = new JSONObject();
            rawThesis.put("value", thesis.getValue());
            rawThesis.put("importance", thesis.getImportance());
            rawThesis.put("sentiment", thesis.getSentiment());

            return rawThesis;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
