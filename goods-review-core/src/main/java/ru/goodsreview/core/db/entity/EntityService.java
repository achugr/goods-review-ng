package ru.goodsreview.core.db.entity;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.core.util.Batch;
import ru.goodsreview.core.util.IterativeBatchPreparedStatementSetter;
import ru.goodsreview.core.util.Md5Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 21:21
 */
public class EntityService {
    private final static Logger log = Logger.getLogger(EntityService.class);

    private final static String TYPE_ID_ATTR = "typeId";
    private final static String ID_ATTR = "id";

    private JdbcTemplate jdbcTemplate;


    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeEntities(final Collection<JSONObject> entities) {

        final Batch<StorageEntity> batchForWrite = new Batch<StorageEntity>() {
            @Override
            public void handle(final List<StorageEntity> storageEntities) {
                log.debug("batch for write flushed");
                jdbcTemplate.batchUpdate("INSERT INTO ENTITY (ENTITY_ATTRS, ENTITY_HASH, ENTITY_TYPE_ID, ENTITY_ID, WATCH_DATE) VALUES (?, ?, ?, ?, ?)",
                        EntityBatchPreparedStatementSetter.of(storageEntities));
            }
        };

        final Batch<StorageEntity> batchForUpdate = new Batch<StorageEntity>() {
            @Override
            public void handle(final List<StorageEntity> storageEntities) {
                log.debug("batch for update flushed");
                jdbcTemplate.batchUpdate("UPDATE ENTITY SET ENTITY_ATTRS = ?, ENTITY_HASH = ?, WATCH_DATE = ? WHERE ENTITY_TYPE_ID = ? AND ENTITY_ID = ?",
                        EntityBatchPreparedStatementSetter.of(storageEntities));
            }
        };

        //TODO this in batch
        for (final JSONObject entity : entities) {

            try {
                final String hash = Md5Helper.hash(entity.toString());
                final long typeId = Long.parseLong(entity.getString(TYPE_ID_ATTR));
                final long id = Long.parseLong(entity.getString(ID_ATTR));

                final List<String> oldHash = jdbcTemplate.query("SELECT ENTITY_HASH FROM ENTITY WHERE ENTITY_TYPE_ID = ? AND ENTITY_ID = ?", new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("entity_hash");
                    }
                }, typeId, id);
                if (oldHash.size() == 0) {
                    batchForWrite.submit(new StorageEntity(entity, hash, id, typeId));
                } else if (oldHash.size() == 1) {
                    if (!oldHash.get(1).equals(hash)) {
                        batchForUpdate.submit(new StorageEntity(entity, hash, id, typeId));
                    }
                } else {
                    throw new RuntimeException("something wrong in db size = " + oldHash.size());
                }
            } catch (JSONException e) {
                log.error("Wrong entity", e);
                throw new RuntimeException(e);
            }

        }

        batchForUpdate.flush();
        batchForWrite.flush();
    }

    public void updateEntities(final Collection<JSONObject> entities) {
        jdbcTemplate.update("UPDATE ENTITY SET ENTITY_ATTRS = ? WHERE ENTITY_TYPE_ID = ? AND ENTITY_ID = ?",
                new IterativeBatchPreparedStatementSetter<JSONObject>(entities) {
                    @Override
                    protected void setValues(PreparedStatement ps, JSONObject element) throws SQLException {
                        try {
                            ps.setString(1, element.toString());
                            ps.setLong(2, Long.parseLong(element.getString(TYPE_ID_ATTR)));
                            ps.setLong(3, Long.parseLong(element.getString(ID_ATTR)));
                        } catch (JSONException e) {
                            log.error("Wrong entity", e);
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    public void visitEntitiesWithCondition(Condition condition, final Visitor<JSONObject> visitor) {
        //TODO
    }

    public void visitEntities(final long entityTypeId, final Visitor<JSONObject> visitor) {

        jdbcTemplate.query("SELECT ENTITY_ATTRS FROM ENTITY WHERE ENTITY_TYPE_ID = ?", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                try {
                    visitor.visit(new JSONObject(rs.getString("entity_attrs")));
                } catch (JSONException e) {
                    log.error("Critical - smth wrong with entities in db");
                    //throw new RuntimeException(e);
                }
            }
        }, entityTypeId);
    }

}
