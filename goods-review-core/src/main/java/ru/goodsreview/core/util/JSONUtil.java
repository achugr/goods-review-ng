package ru.goodsreview.core.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 08.07.12
 */
public class JSONUtil {

    public static List<JSONObject> convertJSONArrayToListOfJSONObjects(final JSONArray jsonArray) {
        return new AbstractList<JSONObject>() {
            @Override
            public JSONObject get(final int i) {
                try {
                    return jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public int size() {
                return jsonArray.length();
            }
        };
    }

    public static List<JSONObject> extractList(JSONObject mainObject, final String key, final String... parents) throws JSONException {
        if (mainObject == null) throw new NullPointerException("JSON object is null");

        //go down by json to needed key
        for (String parent : parents) {
            mainObject = mainObject.getJSONObject(parent);
        }
        //get JSONArray by specified key and cast it to List<JSONObject>
        return convertJSONArrayToListOfJSONObjects(mainObject.getJSONArray(key));
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

    public static JSONArray unsafeGetJsonArray(final JSONObject jsonObject, final String parameterName) {
        try {
            return jsonObject.getJSONArray(parameterName);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static List<String> jsonArrayToStringList(final JSONArray jsonArray) throws JSONException {
        final List<String> result = new ArrayList<String>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }

        return result;
    }

    public static List<String> unsafeJsonArrayToStringList(final JSONArray jsonArray) {
        try {
            return jsonArrayToStringList(jsonArray);
        } catch (JSONException e) {
            return new ArrayList<String>();
        }
    }
}
