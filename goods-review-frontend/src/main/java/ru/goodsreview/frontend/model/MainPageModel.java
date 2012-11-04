package ru.goodsreview.frontend.model;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.frontend.core.SettingsHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class MainPageModel {
    private final static Logger log = Logger.getLogger(MainPageModel.class);

    public List<JSONObject> getPopularProducts(final int productsNumber) {
//        TODO some logic here
//        TODO it's temporarily solution
//        TODO fix bug with parameters in sql query
        return SettingsHolder.getJdbcTemplate().query("select ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID=1 and " +
                "ENTITY_ATTRS like \'%\"reviewsCount\":__,%\' and ENTITY_ATTRS like \'%\"rating\":5%\' " +
                "order by RAND() limit ?",
                new Object[]{productsNumber},
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
