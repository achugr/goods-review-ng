package ru.goodsreview.core.util;

import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:15
 */
public abstract class IterativeBatchPreparedStatementSetter<T> extends AbstractInterruptibleBatchPreparedStatementSetter {

    private final Iterator<T> iterator;

    public IterativeBatchPreparedStatementSetter(final Iterable<T> iterable) {
        this.iterator = iterable.iterator();
    }

    @Override
    protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
        if (iterator.hasNext()) {
            setValues(ps, iterator.next());
            return true;
        }
        return false;
    }

    protected abstract void setValues(PreparedStatement ps, T element) throws SQLException;
}
