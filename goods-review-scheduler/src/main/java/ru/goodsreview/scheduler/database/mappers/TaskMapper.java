package ru.goodsreview.scheduler.database.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.scheduler.TaskParameters;
import ru.goodsreview.scheduler.context.JsonContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: achugr
 * Date: 27.06.12
 * Email: achugr@yandex-team.ru
 *
 *
 *
 * //TODO надо использовать параметризированный row mapper
 * //TODO ловить эксепшены тут да и еще которые не кидаются тут непонятно
 */
public class TaskMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            TaskParameters taskParameters = new TaskParameters(resultSet.getLong("id"),
                    resultSet.getString("bean_name"), JsonContext.from(resultSet.getString("params")));
        } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        TODO fix this
        return "";

    }
}
