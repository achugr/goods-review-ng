package ru.goodsreview.scheduler;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.goodsreview.core.util.IterativeBatchPreparedStatementSetter;
import ru.goodsreview.scheduler.util.MappersHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 12.07.12
 * Time: 23:57
 */
public class TimeTableService {
    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void updateTime(final List<Long> ids, final String rowName) {
        jdbcTemplate.batchUpdate("UPDATE TASK SET " + rowName + " = CURRENT_TIMESTAMP WHERE ID = ?",
                new IterativeBatchPreparedStatementSetter<Long>(ids) {
                    @Override
                    protected void setValues(final PreparedStatement ps, final Long element) throws SQLException {
                        ps.setLong(1, element);
                    }
                });
    }

    public void updateLastRunTime(final List<Long> ids) {
        updateTime(ids, "LAST_RUN_TIME");
    }

    public void updateLastPingTime(final List<Long> ids) {
        updateTime(ids, "LAST_PING_TIME");
    }

    //TODO возвращать просто List ой как плохо
    public List urgentTasks() {
        long currentTime = System.currentTimeMillis();                                                              //TODO не надо закутывать в обжект
        return jdbcTemplate.query("select id, bean_name, params from task where (? - last_run < scheduling_time) ", new Object[]{currentTime}, MappersHolder.TASK_PARAMETERS_MAPPER);
    }

    public void addTaskResult(final TaskResult taskResult, final long taskId) {
        jdbcTemplate.update("INSERT INTO TASK_RESULT (TASK_ID, STATUS, MESSAGE) VALUES (?, ?, ?)", taskId, taskResult.getStatus(), taskResult.getMessage());
    }
}
