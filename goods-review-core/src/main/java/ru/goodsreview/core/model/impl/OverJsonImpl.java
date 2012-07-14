package ru.goodsreview.core.model.impl;

import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityType;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 16:53
 */
public abstract class OverJsonImpl {

    protected final JSONObject jsonObject;

    public OverJsonImpl(final JSONObject jsonObject, final EntityType entityType) {
        if (entityType.equals(EntityType.recognizeEntity(jsonObject))) {
            this.jsonObject = jsonObject;
        } else {
            throw new IllegalArgumentException("illegal inner json object");
        }
    }
}
