package ru.goodsreview.scheduler;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import ru.goodsreview.core.util.IterativeBatchPreparedStatementSetter;
import ru.goodsreview.core.util.db.DbUtil;
import ru.goodsreview.scheduler.context.JsonContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: daddy-bear
 * Date: 12.07.12
 * Time: 23:57
 */
public class TimeTableService {
    private final static Logger log = Logger.getLogger(TimeTableService.class);

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

    public List<TaskParameters> fetchNewTasks(final String schedulerName, final Collection<Long> alreadyRunningTasks) {
        final List<TaskParameters> newTasks = new ArrayList<TaskParameters>();
        final long currentMillis = System.currentTimeMillis();
        jdbcTemplate.query("SELECT * FROM TASK WHERE SCHEDULER_NAME = ? AND ID NOT IN " + DbUtil.createInSection(alreadyRunningTasks.size()), new RowCallbackHandler() {
            @Override
            public void processRow(final ResultSet rs) throws SQLException {

                final long lastRunTime = rs.getLong("LAST_RUN_TIME");
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
                    newTasks.add(new TaskParameters(rs.getLong("ID"), rs.getString("BEAN_NAME"), JsonContext.from(rs.getString("PARAM"))));
                } catch (JSONException e) {
                    log.error("could not resolve task parameters", e);
                }
            }
        }, schedulerName, alreadyRunningTasks);

        return newTasks;
    }

    public void addTaskResult(final TaskResult taskResult, final long taskId) {
        jdbcTemplate.update("INSERT INTO TASK_JOURNAL (TASK_ID, STATUS, MESSAGE) VALUES (?, ?, ?)", taskId, taskResult.getStatus(), taskResult.getMessage());
    }


    private final static String NEW_TASK_STATUS = "STARTED";
    private final static String NEW_TASK_MESSAGE = "new task started";

    public void addNewRunningTask(List<Long> taskIds) {
        jdbcTemplate.batchUpdate("INSERT INTO TASK_JOURNAL (TASK_ID, STATUS, MESSAGE) VALUES (?, ?, ?)", new IterativeBatchPreparedStatementSetter<Long>(taskIds) {
            @Override
            protected void setValues(final PreparedStatement ps, final Long element) throws SQLException {
                ps.setLong(1, element);
                ps.setString(2, NEW_TASK_STATUS);
                ps.setString(3, NEW_TASK_MESSAGE);
            }
        });
    }
}
