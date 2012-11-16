package ru.goodsreview.analyzer;


import org.json.JSONException;
import org.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.scheduler.SchedulerTask;
import ru.goodsreview.scheduler.TaskResult;
import ru.goodsreview.scheduler.context.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * User: ilya
 * Date: 16.11.12
 */
public class ProductInfo {

    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void test(){

        final String[] searchWords = {"123"};

        List<JSONObject> searchResults = jdbcTemplate.query(new PreparedStatementCreator() {
                                                                    @Override
                                                                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                                                        StringBuilder searchSql = new StringBuilder("select ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 1");
                                                                        for (int i = 1; i <= searchWords.length; i++) {
                                                                            searchSql.append(" or ENTITY_ATTRS like ?");
                                                                        }
                                                                        PreparedStatement searchStatement = con.prepareStatement(searchSql.toString());
                                                                        for (int i = 1; i <= searchWords.length; i++) {
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
                                                                }
        );

        for (JSONObject object1:searchResults){
            System.out.println(object1);
        }

    }
}