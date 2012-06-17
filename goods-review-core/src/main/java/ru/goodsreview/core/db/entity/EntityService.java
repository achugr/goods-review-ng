package ru.goodsreview.core.db.entity;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.goodsreview.core.util.Batch;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 21:21
 */
public class EntityService {

    private final static String TYPE_ID_ATTR = "typeId";
    private final static String ID_ATTR = "id";

    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeEntities(final Collection<JsonObject> entities) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        final Batch<StorageEntity> batchForWrite = new Batch<StorageEntity>() {
            @Override
            public void handle(final List<StorageEntity> storageEntities) {
                jdbcTemplate.batchUpdate("INSERT INTO entity (entity_attrs, entity_hash, entity_type_id, entity_id) VALUES (?, ?, ?, ?)",
                        EntityBatchPreparedStatementSetter.of(storageEntities));
            }
        };

        final Batch<StorageEntity> batchForUpdate = new Batch<StorageEntity>() {
            @Override
            public void handle(final List<StorageEntity> storageEntities) {
                jdbcTemplate.batchUpdate("UPDATE entity SET entity_attrs = ?, entity_hash = ?, watch_date = ? WHERE entity_type_id = ? AND entity_id = ?",
                        EntityBatchPreparedStatementSetter.of(storageEntities));
            }
        };


        for (final JsonObject entity : entities) {
            digest.update(entity.toString().getBytes());
            final String hash = new String(digest.digest());
            final long typeId = entity.get(TYPE_ID_ATTR).getAsLong();
            final long id = entity.get(ID_ATTR).getAsLong();

            String oldHash = jdbcTemplate.queryForObject("SELECT hash FROM entity WHERE entity_type_id = ? AND entity_id = ?", String.class);
            if (oldHash == null) {
                batchForWrite.submit(new StorageEntity(entity, hash, id, typeId));
            } else if (!oldHash.equals(hash)) {
                batchForUpdate.submit(new StorageEntity(entity, hash, id, typeId));
            }
            digest.reset();
        }
    }

}
