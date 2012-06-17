package ru.goodsreview.core.db.entity;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.goodsreview.core.util.Batch;
import ru.goodsreview.core.util.IterativeBatchPreparedStatementSetter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            public void handle(List<StorageEntity> storageEntities) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        final Batch<StorageEntity> batchForUpdate = new Batch<StorageEntity>() {
            @Override
            public void handle(List<StorageEntity> storageEntities) {
                jdbcTemplate.batchUpdate("UPDATE entity SET entity_attrs = ?, entity_hash = ? WHERE entity_type_id = ? AND entity_id = ?", new IterativeBatchPreparedStatementSetter<StorageEntity>(storageEntities) {
                    @Override
                    protected void setValues(PreparedStatement ps, StorageEntity element) throws SQLException {
                        ps.setString(1, element.getEntity().toString());
                        ps.setString(2, element.getHash());
                        ps.setLong(3, element.getTypeId());
                        ps.setLong(4, element.getId());
                    }
                });
            }
        };


        for (final JsonObject entity: entities) {
            digest.update(entity.toString().getBytes());
            final String hash = new String(digest.digest());
            final long typeId = entity.get(TYPE_ID_ATTR).getAsLong();
            final long id = entity.get(ID_ATTR).getAsLong();

            String oldHash = jdbcTemplate.queryForObject("SELECT hash FROM entity WHERE entity_type_id = ? AND entity_id = ?", String.class);
            if (oldHash == null) {

            }
            digest.reset();
        }
    }

    private class StorageEntity {

        private final JsonObject entity;
        private final String hash;
        private final long id;
        private final long typeId;

        private StorageEntity(JsonObject entity, String hash, long id, long typeId) {
            this.entity = entity;
            this.hash = hash;
            this.id = id;
            this.typeId = typeId;
        }

        public JsonObject getEntity() {
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
}
