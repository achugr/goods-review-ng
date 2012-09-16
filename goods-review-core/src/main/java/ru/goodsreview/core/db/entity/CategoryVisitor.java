package ru.goodsreview.core.db.entity;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;

/**
 * User: YaroslavSkudarnov
 * Date: 15.09.12
 * Time: 16:01
 */
public class CategoryVisitor {
    private final static Logger log = Logger.getLogger(CategoryVisitor.class);

    @Autowired
    private EntityService entityService;

    public Collection visit(final int categoryId) {
        final Collection<JSONObject> set = new HashSet<JSONObject>();

        entityService.visitEntities(3, new Visitor<JSONObject>() {
            @Override
            public void visit(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("categoryId") == categoryId)
                    set.add(jsonObject);
                } catch (JSONException e) {
                    log.error("Error: can't get entities by category with id " + categoryId);
                }
            }
        });

        return set;
    }
}
