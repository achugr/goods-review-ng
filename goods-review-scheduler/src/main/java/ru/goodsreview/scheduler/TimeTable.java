package ru.goodsreview.scheduler;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.goodsreview.scheduler.TaskResult;

/**
 * User: daddy-bear
 * Date: 12.07.12
 * Time: 23:57
 */
public class TimeTable {
    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTaskResult(final TaskResult taskResult, final long taskId){
        jdbcTemplate.update("INSERT INTO TASK_RESULT (TASK_ID, STATUS, MESSAGE) VALUES (?, ?, ?)", taskId, taskResult.getStatus(), taskResult.getMessage());
    }
}
