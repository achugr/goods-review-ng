package ru.goodsreview.scheduler.database.controllers;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.goodsreview.scheduler.util.MappersHolder;

import java.util.List;

/**
 * User: achugr
 * Date: 26.06.12
 * Email: achugr@yandex-team.ru
 *  * //TODO зачем в сеттере и в конструкторе передавать jdbcTemplate?
 */
public class TaskDbController {
    private JdbcTemplate jdbcTemplate;

    public TaskDbController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Required
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

                                                                                                                                          //TODO надо сделать константу, а не каждый раз новый делать

}
