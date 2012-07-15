package ru.goodsreview.core.db.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 14:41
 */
public enum EntityType {

    MODEL(1L),
    REVIEW(2L),
    CATEGORY(3L);

    public final static String TYPE_ID_ATTR = "typeId";

    private final long typeId;

    private EntityType(final long typeId) {
        this.typeId = typeId;
    }

    public long getTypeId() {
        return typeId;
    }

    public static EntityType getByTypeId(final long typeId) {
        for (EntityType entityType: values()) {
            if (entityType.getTypeId() == typeId) {
                return entityType;
            }
        }
        throw new IllegalArgumentException("no entity type with id = " + typeId);
    }

    public static EntityType recognizeEntity(final JSONObject jsonObject) {
        try{
            final long typeId = Long.parseLong(jsonObject.getString(TYPE_ID_ATTR));
            return getByTypeId(typeId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
