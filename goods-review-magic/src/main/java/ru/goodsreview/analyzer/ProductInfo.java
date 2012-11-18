package ru.goodsreview.analyzer;


import org.json.JSONException;
import org.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.goodsreview.analyzer.util.ProductInfoPreparatory;
import ru.goodsreview.core.db.entity.EntityBatchPreparedStatementSetter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * User: ilya
 * Date: 16.11.12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class ProductInfo {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void update(JSONObject object){
        String  query = "INSERT INTO ENTITY (ENTITY_ATTRS, ENTITY_HASH, WATCH_DATE, ENTITY_TYPE_ID, ENTITY_ID) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)";

        jdbcTemplate.update(query, new Object[] { object.toString(),  0 , 4, 0});
    }


    public Set<Integer> getModelsId(){
        HashSet<Integer> set = new HashSet<Integer>();

        final List<JSONObject> searchResults = jdbcTemplate.query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 2",
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


        for (JSONObject object1:searchResults){
            try {
                set.add(Integer.parseInt(object1.get("modelId").toString()));
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

       return set;
    }

    public void prepareModel(int modelId){
        final List<JSONObject> searchResults = jdbcTemplate.query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 2 AND ENTITY_ATTRS like ?",
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


//        for (JSONObject object1:searchResults){
//            System.out.println(object1);
//        }

        JSONObject res = ProductInfoPreparatory.prepareInfo(modelId, searchResults);
     //   System.out.println(res.toString());
        update(res);
    }

    @Test
    public void test(){
//        Set<Integer> set = getModelsId();
//       // System.out.println(set.size());
//        int k = 0;
//        for (Integer i:set){
//            if(k%100==0){
//                System.out.println(k);
//            }
//            prepareModel(i);
//            k++;
//        }
         prepareModel(8290995);
       // int modelId = 6504630;
      //  int modelId = 6456057;

    }

    @Test
    public void clear(){
        String query = "delete from ENTITY where  ENTITY_TYPE_ID = 4";
        jdbcTemplate.update(query);

    }


}