package ru.goodsreview.scheduler.database.controllers;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.goodsreview.scheduler.TaskResult;

/**
 * User: achugr
 * Date: 27.06.12
 * Email: achugr@yandex-team.ru
 *
 *
 * //TODO зачем в сеттере и в конструкторе передавать jdbcTemplate?
 */
public class TaskResultDbController {
    private JdbcTemplate jdbcTemplate;

    public TaskResultDbController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Required
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTaskResult(TaskResult taskResult){
        jdbcTemplate.update("insert into task_result (status, message) values (?, ?)",
                taskResult.getStatus(), taskResult.getMessage());
    }
}
