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

    protected static final String VALUE_ATTR = "value";
    protected static final String IMPORTANCE_ATTR = "value";
    protected static final String SENTIMENT_ATTR = "value";

    protected final JSONObject jsonObject;

    public ThesisOverJson(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String getValue() {
        return unsafeGetString(jsonObject, VALUE_ATTR);
    }

    @Override
    public double getImportance() {
        return Double.parseDouble(unsafeGetString(jsonObject, IMPORTANCE_ATTR));
    }

    @Override
    public double getSentiment() {
        return Double.parseDouble(unsafeGetString(jsonObject, SENTIMENT_ATTR));
    }
}
