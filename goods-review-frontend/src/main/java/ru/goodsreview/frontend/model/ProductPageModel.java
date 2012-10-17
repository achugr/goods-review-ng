package ru.goodsreview.frontend.model;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.db.visitor.Visitor;
import ru.goodsreview.frontend.core.SettingsHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
public class ProductPageModel {

    private static final Logger log = Logger.getLogger(ProductPageModel.class);

    private EntityService entityService;

    @Required
    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    public JSONObject getModelById(final long modelId) {
//        TODO it's govnokod
        return SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 1 AND ENTITY_ATTRS like ?",
                new String[]{"%modelid="+modelId+"%"},
                new RowMapper<JSONObject>() {
                    @Override
                    public JSONObject mapRow(ResultSet rs, int line) throws SQLException, DataAccessException {
                        try {
                            return new JSONObject(rs.getString("ENTITY_ATTRS"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).get(0);

//        entityService.visitEntities(1, new Visitor<JSONObject>() {
//            @Override
//            public void visit(JSONObject jsonObject) {
//                try {
//                    final String link = jsonObject.getString("link");
//                    long modelId = Long.parseLong(link.substring(link.indexOf("modelid="), link.indexOf("&")));
//                    if (productId == modelId) {
//                        result.add(jsonObject);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//        });
//        return result.get(0);

//        final List<JSONObject> result = new LinkedList<JSONObject>();
//        final Visitor<JSONObject> visitor = new Visitor<JSONObject>() {
//            @Override
//            public void visit(JSONObject jsonObject) {
//                final String link;
//                try {
//                    link = jsonObject.getString("link");
//                    long modelId = Long.parseLong(link.substring(link.indexOf("modelid=") + "modelid=".length(), link.indexOf("&")));
//                    if (productId == modelId) {
//                        result.add(jsonObject);
//                        System.out.println(jsonObject.toString());
//                    }
//                } catch (JSONException e) {
////                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                } catch (StringIndexOutOfBoundsException e){
//
//                }
//            }
//        };
//
//        SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS FROM ENTITY WHERE ENTITY_TYPE_ID = 1", new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet rs) throws SQLException {
//                try {
//                    visitor.visit(new JSONObject(rs.getString("ENTITY_ATTRS")));
//                } catch (JSONException e) {
//                    log.error("Critical - smth wrong with entities in db");
//                    //throw new RuntimeException(e);
//                }
//            }
//        });
//        return result.get(0);
    }

//    TODO it's hard-hard-hard-hard code
    public List<JSONObject> getReviewsByModelId(final long modelId){

        return SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 2 AND ENTITY_ATTRS like ?",
                new String[]{"%"+modelId+"%"},
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

//        final List<JSONObject> result = new LinkedList<JSONObject>();
//        final Visitor<JSONObject> visitor = new Visitor<JSONObject>() {
//            @Override
//            public void visit(JSONObject jsonObject) {
//                try {
//                    long currentModelId = jsonObject.getLong("modelId");
//                    if (modelId == currentModelId) {
//                        result.add(jsonObject);
//                        System.out.println(jsonObject.toString());
//                    }
//                } catch (JSONException e) {
////                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                } catch (StringIndexOutOfBoundsException e){
//
//                }
//            }
//        };
//
//        SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS FROM ENTITY WHERE ENTITY_TYPE_ID = 2", new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet rs) throws SQLException {
//                try {
//                    visitor.visit(new JSONObject(rs.getString("ENTITY_ATTRS")));
//                } catch (JSONException e) {
//                    log.error("Critical - smth wrong with entities in db");
//                    //throw new RuntimeException(e);
//                }
//            }
//        });
//        return result;


    }
}
