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
 * User: timur
 * Date: 18.10.12
 * Time: 15:45
 */
public class CategoryModel {
    private final static int MODELS_ON_PAGE_NUM = 9;

    public int getModelsCount(final long categoryId) {
        return SettingsHolder.getJdbcTemplate().queryForObject("SELECT COUNT(ENTITY_ATTRS) from ENTITY where ENTITY_TYPE_ID = 1 AND ENTITY_ATTRS like ?",
                new String[]{"%\"categoryId\":" + categoryId + "%"}, Integer.class);
    }

    public List<JSONObject> getModelsByCategoryId(final long categoryId, final int pageNumber) {
        final int indexFrom = (pageNumber - 1) * MODELS_ON_PAGE_NUM;
        return SettingsHolder.getJdbcTemplate().query(
                "SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 1 AND ENTITY_ATTRS like ? LIMIT ?, ?",
                new Object[]{"%\"categoryId\":" + categoryId + "%", indexFrom, MODELS_ON_PAGE_NUM},
                new RowMapper<JSONObject>() {
                    @Override
                    public JSONObject mapRow(ResultSet rs, int line) throws SQLException, DataAccessException {
                        try {
                            return new JSONObject(rs.getString("ENTITY_ATTRS"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }
}
