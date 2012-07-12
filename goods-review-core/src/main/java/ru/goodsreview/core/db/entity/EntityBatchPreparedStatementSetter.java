package ru.goodsreview.core.db.entity;

import ru.goodsreview.core.util.IterativeBatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:29
 */
public class EntityBatchPreparedStatementSetter extends IterativeBatchPreparedStatementSetter<StorageEntity>{

    public EntityBatchPreparedStatementSetter(final Iterable<StorageEntity> storageEntities) {
        super(storageEntities);
    }

    @Override
    protected void setValues(final PreparedStatement ps, final StorageEntity element) throws SQLException {
        ps.setString(1, element.getEntity().toString());
        ps.setString(2, element.getHash());
        ps.setLong(3, element.getTypeId());
        ps.setLong(4, element.getId());
    }

    public static EntityBatchPreparedStatementSetter of(final Iterable<StorageEntity> storageEntities) {
        return new EntityBatchPreparedStatementSetter(storageEntities);
    }
}
