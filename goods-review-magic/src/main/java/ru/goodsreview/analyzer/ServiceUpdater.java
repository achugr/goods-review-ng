package ru.goodsreview.analyzer;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.db.entity.EntityService;

import java.util.Collection;

/**
 * Date: 01.10.12
 * Ilya Makeev
 */
public class ServiceUpdater implements EntityUpdater {
    private static final Logger log = Logger.getLogger(ServiceUpdater.class);

    private EntityService entityService;

    @Required
    public void setEntityService(final EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public void update(Collection<JSONObject> entities) {
        log.info("Entities for improving: ");
        for(JSONObject entity : entities){
            log.info(entity.toString());
        }
        entityService.improveEntities(entities);
        log.info("Entities were improved..");
    }
}
