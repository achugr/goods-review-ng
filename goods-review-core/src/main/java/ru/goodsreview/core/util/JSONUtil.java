package ru.goodsreview.core.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 08.07.12
 */
public class JSONUtil {

    public static List<JSONObject> convertJSONArrayToListOfJSONObjects(final JSONArray jsonArray) {
        final List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.optJSONObject(i));
        }
        return jsonObjects;
    }

    public static String unsafeGetString(final JSONObject jsonObject, final String parameterName) {
        try {
            return jsonObject.getString(parameterName);
        } catch (JSONException e) {
            return StringUtil.EMPTY;
        }
    }

    public static JSONObject unsafeGetJsonObject(final JSONObject jsonObject, final String parameterName) {
        try {
            return jsonObject.getJSONObject(parameterName);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
