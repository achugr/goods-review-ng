package ru.goodsreview.core.model.impl;

import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.model.Model;

import static ru.goodsreview.core.util.JSONUtil.unsafeGetString;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 15:14
 */
public class ModelOverJson extends OverJsonImpl implements Model {

    public ModelOverJson(final JSONObject jsonObject) {
        super(jsonObject, EntityType.MODEL);
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
