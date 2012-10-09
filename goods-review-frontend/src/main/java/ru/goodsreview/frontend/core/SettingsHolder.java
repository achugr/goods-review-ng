package ru.goodsreview.frontend.core;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Dmitry Batkovich <daddy-bear@yandex-team.ru>
 */
public class SettingsHolder {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public SettingsHolder(){}

    public static class SettingsController {

        public SettingsController(){}

        @Required
        public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
            SettingsHolder.jdbcTemplate = jdbcTemplate;
        }

    }
}
