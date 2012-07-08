package ru.goodsreview.core.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 08.07.12
 */
public class JSONUtil {

    public static List<JSONObject> convertJSONArrayToListOfJSONObjects(JSONArray jsonArray) {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.optJSONObject(i));
        }
        return jsonObjects;
    }
}
