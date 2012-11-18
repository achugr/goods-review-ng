package ru.goodsreview.frontend.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.frontend.core.SettingsHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
 */
public class SearchPageModel {

    public List<JSONObject> getSearchResults(final String searchQuery) {
        final String[] searchWords = searchQuery.split("\\+");

        List<JSONObject> searchResults = SettingsHolder.getJdbcTemplate().query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                StringBuilder searchSql = new StringBuilder("select ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 1");
                for(int i = 1; i <= searchWords.length; i++){
                    searchSql.append(" or ENTITY_ATTRS like ?");
                }
                PreparedStatement searchStatement = con.prepareStatement(searchSql.toString());
                for(int i = 1; i <= searchWords.length; i++){
                    searchStatement.setString(i, "%" + searchWords[i] + "%");
                }
                return searchStatement;
            }
        }, new RowMapper<JSONObject>() {
            @Override
            public JSONObject mapRow(ResultSet rs, int line) throws SQLException, DataAccessException {
                try {
                    return new JSONObject(rs.getString("ENTITY_ATTRS"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return searchResults;
    }
}
