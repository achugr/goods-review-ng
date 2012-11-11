package ru.goodsreview.frontend.model;

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
 *
 *        hardcoded class should be replaced by searcher
 */
public class MainPageModel {
    public List<JSONObject> getPopularProducts(final int productsNumber) {
        return SettingsHolder.getJdbcTemplate().query("select ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID=1 and " +
                "ENTITY_ATTRS like \'%\"rating\":5%\' and ENTITY_ATTRS like \'%\"opinionsCount\":__}%\'" +
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
