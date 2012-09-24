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
public class ReviewHelper {
    private final static Logger log = Logger.getLogger(CategoryHelper.class);

    @Autowired
    private EntityService entityService;

    public Collection visit(final long modelId) {
        final Collection<JSONObject> set = new HashSet<JSONObject>();

        entityService.visitEntities(2, new Visitor<JSONObject>() {
            @Override
            public void visit(JSONObject jsonObject) {
                try {
                    if (jsonObject.getLong("modelId") == modelId) {
                        set.add(jsonObject);
                        System.out.println(jsonObject.toString());
                    }
                } catch (JSONException e) {
                    log.error(e.getMessage(), e);
                }
            }
        });

        return set;
    }
}
