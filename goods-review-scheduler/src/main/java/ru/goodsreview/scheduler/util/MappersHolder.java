package ru.goodsreview.scheduler.util;

import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.scheduler.TaskParameters;
import ru.goodsreview.scheduler.context.JsonContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: achugr
 * Date: 27.06.12
 * Email: achugr@yandex-team.ru
 */

public class MappersHolder {

    private static class TaskParametersMapper implements RowMapper<TaskParameters> {
        @Override
        public TaskParameters mapRow(ResultSet resultSet, int i) throws SQLException {
            return new TaskParameters(resultSet.getLong("ID"), resultSet.getString("BEAN_NAME"), JsonContext.from(resultSet.getString("PARAMS")));
        }
    }

    public static final TaskParametersMapper TASK_PARAMETERS_MAPPER = new TaskParametersMapper();
}
