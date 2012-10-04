package ru.goodsreview.analyzer;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.util.Batch;
import ru.goodsreview.core.db.visitor.Visitor;
import ru.goodsreview.scheduler.SchedulerTask;
import ru.goodsreview.scheduler.TaskResult;
import ru.goodsreview.scheduler.context.Context;

import java.util.List;

/**
* @author daddy-bear
*         Date: 22.06.12
*/
public abstract class AnalyzingSchedulerTask implements SchedulerTask {
    private static final Logger log = Logger.getLogger(AnalyzingSchedulerTask.class);

    private EntityService entityService;

    private EntityUpdater entityUpdater;

    @Required
    public void setEntityService(final EntityService entityService) {
        this.entityService = entityService;
    }

    @Required
    public void setEntityUpdater(final EntityUpdater entityUpdater) {
        this.entityUpdater = entityUpdater;
    }

    @Override
    public TaskResult run(final Context context) {
        log.info("AnalyzingSchedulerTask started..");

        final Batch<JSONObject> batchUpdater = new Batch<JSONObject>() {
            @Override
            public void handle(final List<JSONObject> jsonObjects) {
                entityUpdater.update(jsonObjects);
            }
        };

        entityService.visitEntities(EntityType.REVIEW.getTypeId(), new Visitor<JSONObject>() {
            @Override
            public void visit(final JSONObject object) {
                batchUpdater.submit(process(object));
            }
        });

        batchUpdater.flush();

        return TaskResult.ok();
    }

    protected abstract JSONObject process(final JSONObject object);
}
