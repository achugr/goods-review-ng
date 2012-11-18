package ru.goodsreview.frontend.core;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Dmitry Batkovich <daddy-bear@yandex-team.ru>
 */
public class SettingsHolder {
    private static JdbcTemplate JDBC_TEMPLATE;

    public static JdbcTemplate getJdbcTemplate() {
        return JDBC_TEMPLATE;
    }

    private SettingsHolder() {
    }

    public static class SettingsController {
        @Required
        public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
            SettingsHolder.JDBC_TEMPLATE = jdbcTemplate;
        }
    }
}
