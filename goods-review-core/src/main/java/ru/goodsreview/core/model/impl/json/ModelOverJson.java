package ru.goodsreview.core.model.impl.json;

import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.model.Model;

import static ru.goodsreview.core.util.JSONUtil.unsafeGetString;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 15:14
 */
public class ModelOverJson implements Model {

    private final JSONObject jsonObject;

    public ModelOverJson(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public long getCategoryId() {
        return Long.parseLong(unsafeGetString(jsonObject, "categoryId"));
    }

    @Override
    public long getId() {
        return Long.parseLong(unsafeGetString(jsonObject, "id"));
    }
}
