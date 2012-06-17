package ru.goodsreview.core.db.entity;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 21:21
 */
public class EntityService {

    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeEntities(final Collection<JsonObject> entities) {

    }
}
