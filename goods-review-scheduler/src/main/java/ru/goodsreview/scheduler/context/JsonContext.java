package ru.goodsreview.scheduler.context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.goodsreview.scheduler.SchedulerException;

import java.util.ArrayList;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 23:03
 *
 *
 */
public class JsonContext extends AbstractContext {

    private final JsonObject jsonObject;

    private JsonContext(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static JsonContext from(final String jsonString) throws SchedulerException {
        return new JsonContext(new Gson().toJsonTree(jsonString).getAsJsonObject());
    }

    @Override
    public String getParam(String paramName) {
        return jsonObject.get(paramName).getAsString();
    }

    @Override
    public List<String> getMultiParam(String paramName) {
        final JsonArray jsonArray = jsonObject.getAsJsonArray(paramName);
        final List<String> result = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            result.add(jsonArray.get(i).getAsString());
        }
        return result;
    }
}
