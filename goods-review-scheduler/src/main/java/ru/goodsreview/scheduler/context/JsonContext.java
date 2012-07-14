package ru.goodsreview.scheduler.context;

import com.sun.istack.internal.Nullable;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:03
 */
public class JsonContext extends AbstractContext {
    private final static Logger log = Logger.getLogger(JsonContext.class);

    private final JSONObject jsonObject;

    private JsonContext(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Nullable
    public static JsonContext from(final String jsonString) throws JSONException {
        if (jsonString.isEmpty()) {
            return new JsonContext(new JSONObject());
        }
        return new JsonContext(new JSONObject(jsonString));
    }

    @Override
    public String getParam(String paramName) {
        try {
            return jsonObject.getString(paramName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getMultiParam(String paramName) {
        try {
            final JSONArray jsonArray = jsonObject.getJSONArray(paramName);
            final List<String> result = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(jsonArray.getString(i));
            }
            return result;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
