package ru.goodsreview.core.db.visitor;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import ru.goodsreview.core.db.entity.EntityService;

import java.util.Collection;
import java.util.HashSet;

/**
 * User: YaroslavSkudarnov
 * Date: 15.09.12
 * Time: 16:01
 */
public class CategoryHelper {
    private final static Logger log = Logger.getLogger(CategoryHelper.class);

    @Autowired
    private EntityService entityService;

    //TODO How many JSONObjects you could visit before OutOfMemory?

    public Collection<JSONObject> visit(final int categoryId) {
        final Collection<JSONObject> set = new HashSet<JSONObject>();

        entityService.visitEntities(3, new Visitor<JSONObject>() {
            @Override
            public void visit(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("id") == categoryId) {
                        set.add(jsonObject);
                        log.info(jsonObject.toString());
                    }
                } catch (JSONException e) {
                    log.error("Error: can't get entities by category with id " + categoryId);
                }
            }
        });

        return set;
    }
}