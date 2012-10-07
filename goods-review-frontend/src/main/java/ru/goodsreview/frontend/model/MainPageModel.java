package ru.goodsreview.frontend.model;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.db.visitor.Visitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class MainPageModel {
    private final static Logger log = Logger.getLogger(MainPageModel.class);

    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<JSONObject> getPopularProducts(final int productsNumber) {
//        TODO some logic here
//        TODO it's temporarily solution
//        TODO fix bug with parameters in sql query
        return jdbcTemplate.query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID=1 LIMIT 4",
                new RowMapper<JSONObject>() {
                    @Override
                    public JSONObject mapRow(ResultSet rs, int line) throws SQLException, DataAccessException {
                        try {
                            return new JSONObject(rs.getString("ENTITY_ATTRS"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

}
