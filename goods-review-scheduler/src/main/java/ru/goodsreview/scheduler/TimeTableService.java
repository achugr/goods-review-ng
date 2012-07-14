package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.goodsreview.core.util.IterativeBatchPreparedStatementSetter;
import ru.goodsreview.scheduler.context.JsonContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 12.07.12
 * Time: 23:57
 */
public class TimeTableService {
    private final static Logger log = Logger.getLogger(TimeTableService.class);

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Required
    public void setNamedParameterJdbcTemplate(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private void updateTime(final List<Long> ids, final String rowName) {
        if (ids.size() == 0) {
            return;
        }
        jdbcTemplate.batchUpdate("UPDATE TASK SET " + rowName + " = ? WHERE ID = ?",
                new IterativeBatchPreparedStatementSetter<Long>(ids) {
                    @Override
                    protected void setValues(final PreparedStatement ps, final Long element) throws SQLException {
                        ps.setLong(1, new Date().getTime());
                        ps.setLong(2, element);
                    }
                });
    }

    private void updateLastRunTime(final List<Long> ids) {
        updateTime(ids, "LAST_RUN");
    }

    public void updateLastPingTime(final List<Long> ids) {
        updateTime(ids, "LAST_PING");
    }


    private final static String BASE_SQL = "SELECT * FROM TASK WHERE SCHEDULER_NAME = :SCH_NAME ";

    public List<TaskInfo> fetchNewTasks(final String schedulerName, final Collection<Long> alreadyRunningTasks) {
        final List<TaskInfo> newTasks = new ArrayList<TaskInfo>();
        final long currentMillis = System.currentTimeMillis();

        String sql = BASE_SQL;
        final MapSqlParameterSource ps = new MapSqlParameterSource();
        ps.addValue("SCH_NAME", schedulerName);

        if (!alreadyRunningTasks.isEmpty()) {
            ps.addValue("ID", alreadyRunningTasks);
            sql += "AND ID NOT IN (:ID)";
        }
        namedParameterJdbcTemplate.query(sql, ps, new RowCallbackHandler() {
            @Override
            public void processRow(final ResultSet rs) throws SQLException {

                final long lastRunTime = rs.getLong("LAST_RUN");
                final SchedulingType schedulingType = SchedulingType.getByName(rs.getString("SCHEDULING_TYPE"));
                if (SchedulingType.ONCE.equals(schedulingType)) {
                    if (lastRunTime != 0) {
                        return;
                    }
                } else {
                    if (currentMillis - lastRunTime < schedulingType.getAbsoluteInterval(rs.getLong("SCHEDULING_TIME"))) {
                        return;
                    }
                }

                try {
                    newTasks.add(new TaskInfo(rs.getLong("ID"), rs.getString("BEAN_NAME"), JsonContext.from(rs.getString("PARAMS"))));
                } catch (JSONException e) {
                    log.error("could not resolve task parameters", e);
                }
            }
        });

        return newTasks;
    }

    public void addTaskResult(final TaskResult taskResult, final long taskId) {
        jdbcTemplate.update("INSERT INTO TASK_JOURNAL (TASK_ID, STATUS, MESSAGE) VALUES (?, ?, ?)", taskId, taskResult.getStatus().toString(), taskResult.getMessage());
    }


    private final static String NEW_TASK_STATUS = "STARTED";
    private final static String NEW_TASK_MESSAGE = "new task started";

    public void addNewRunningTask(final List<Long> taskIds) {
        if (taskIds.size() == 0) {
            return;
        }
        jdbcTemplate.batchUpdate("INSERT INTO TASK_JOURNAL (TASK_ID, STATUS, MESSAGE) VALUES (?, ?, ?)", new IterativeBatchPreparedStatementSetter<Long>(taskIds) {
            @Override
            protected void setValues(final PreparedStatement ps, final Long element) throws SQLException {
                ps.setLong(1, element);
                ps.setString(2, NEW_TASK_STATUS);
                ps.setString(3, NEW_TASK_MESSAGE);
            }
        });

        this.updateLastRunTime(taskIds);
    }
}
