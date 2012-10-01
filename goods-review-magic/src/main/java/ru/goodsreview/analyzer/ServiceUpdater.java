package ru.goodsreview.analyzer;

import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityService;

import java.util.Collection;

/**
 * Date: 01.10.12
 * Ilya Makeev
 */
public class ServiceUpdater implements EntityUpdater{
    EntityService entityService;

    @Override
    public void update(Collection<JSONObject> entities) {
       entityService.improveEntities(entities);
    }
}
