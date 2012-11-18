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
 *
 *         hardcoded class should be replaced by searcher
 */
public class ProductModel {
    public JSONObject getModel(final long modelId) {
//        TODO it's govnokod
        final List<JSONObject> rawModels = SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 1 AND ENTITY_ATTRS like ? limit 1",
                new String[]{"%modelid=" + modelId + "%"},
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
        if (rawModels.isEmpty()) {
            throw new RuntimeException(String.format("couldn't find model with modelId = %s", modelId));
        }
        return rawModels.get(0);
    }

    public List<JSONObject> getReviews(final long modelId) {
        return SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 2 AND ENTITY_ATTRS like ?",
                new String[]{"%" + modelId + "%"},
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
