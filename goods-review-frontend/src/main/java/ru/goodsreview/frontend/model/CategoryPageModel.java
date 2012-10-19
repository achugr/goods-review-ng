package ru.goodsreview.frontend.model;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import ru.goodsreview.core.util.Pair;
import ru.goodsreview.frontend.core.SettingsHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 18.10.12
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class CategoryPageModel {
    private final static Logger log = Logger.getLogger(CategoryPageModel.class);
    
    private final static int MODELS_ON_PAGE_NUM = 9;
    
    private List<JSONObject> getAllModelsByCategoryId(final long categoryId){
        return SettingsHolder.getJdbcTemplate().query("SELECT ENTITY_ATTRS from ENTITY where ENTITY_TYPE_ID = 3 AND ENTITY_ATTRS like ?",
                new String[]{"%\"categoryId\":" + categoryId + "%"},
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

    public Pair<Integer, List<JSONObject>> getModelsByCategoryId(final long categoryId, final int pageNumber) {
        List<JSONObject> allModels = getAllModelsByCategoryId(categoryId);
        int modelsNumber = allModels.size();
        int pagesNumber = modelsNumber % MODELS_ON_PAGE_NUM == 0 ? modelsNumber / MODELS_ON_PAGE_NUM :
                                                                   modelsNumber / MODELS_ON_PAGE_NUM + 1;
        int indexFrom, indexTo;
        if(pageNumber < pagesNumber){
            indexFrom = (pageNumber - 1) * MODELS_ON_PAGE_NUM;
            indexTo = pageNumber * MODELS_ON_PAGE_NUM - 1;
        }else if(pageNumber == pagesNumber){
            indexFrom = (pageNumber - 1) * MODELS_ON_PAGE_NUM;
            indexTo = modelsNumber - 1;
        }else{
            indexFrom = 0;
            indexTo = pagesNumber > 1 ? MODELS_ON_PAGE_NUM - 1 : modelsNumber - 1;
        }
        return new Pair<Integer, List<JSONObject>>(pagesNumber, allModels.subList(indexFrom, indexTo));
    }
}
