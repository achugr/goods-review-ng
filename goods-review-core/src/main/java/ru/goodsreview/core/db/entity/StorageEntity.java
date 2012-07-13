package ru.goodsreview.core.db.entity;

import org.json.JSONObject;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:30
 */
class  StorageEntity {
    private final JSONObject entity;
    private final String hash;
    private final long id;
    private final long typeId;

    public StorageEntity(final JSONObject entity, final String hash, final long id, final long typeId) {
        this.entity = entity;
        this.hash = hash;
        this.id = id;
        this.typeId = typeId;
    }

    public JSONObject getEntity() {
        return entity;
    }

    public String getHash() {
        return hash;
    }

    public long getId() {
        return id;
    }

    public long getTypeId() {
        return typeId;
    }
}
