package ru.goodsreview.core.util;

import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:15
 */
public abstract class IterativeBatchPreparedStatementSetter<T> implements InterruptibleBatchPreparedStatementSetter {

    private final Iterator<T> iterator;


    public IterativeBatchPreparedStatementSetter(final Iterable<T> iterable) {
        this.iterator = iterable.iterator();
    }

    @Override
    public boolean isBatchExhausted(int i) {
        return !iterator.hasNext();
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        setValues(ps, iterator.next());
    }

    protected abstract void setValues(PreparedStatement ps, T element) throws SQLException;

    @Override
    public int getBatchSize() {
        return Integer.MAX_VALUE;
    }
}
