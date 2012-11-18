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
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
 */
public class SearchPageModel {

    private enum OPERATOR{
        AND("and"),
        OR("or");

        String name;
        
        OPERATOR(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    };

    private List<JSONObject> searhWordsWithOperator(final String[] searchWords, final OPERATOR operator) {
        return SettingsHolder.getJdbcTemplate().query(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        StringBuilder searchSql = new StringBuilder("select ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 1 and ( ");
                        for(int i = 0; i < searchWords.length; i++){
                            searchSql.append(" ENTITY_ATTRS like ?");
                            if(i != searchWords.length - 1){
                                searchSql.append(" " + operator.getName());
                            }
                        }
                        searchSql.append(" )");
                        System.out.println(searchSql.toString());
                        PreparedStatement searchStatement = con.prepareStatement(searchSql.toString());
                        for(int i = 0; i < searchWords.length; i++){
                            searchStatement.setString(i+1, "%\"name\":\"%" + searchWords[i] + "%");
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
    }

    public List<JSONObject> getSearchResults(final String searchQuery) {
        String trimedSearchQuery = searchQuery.trim();
        if(!trimedSearchQuery.equals("")){
            final String[] searchWords = trimedSearchQuery.split(" ");
            List<JSONObject> searchResultsForAndOp = searhWordsWithOperator(searchWords, OPERATOR.AND);
            if(searchResultsForAndOp.size() != 0){
                return searchResultsForAndOp;
            }else{
                return searhWordsWithOperator(searchWords, OPERATOR.OR);
            }
        }
        return new ArrayList<JSONObject>();
    }
}
