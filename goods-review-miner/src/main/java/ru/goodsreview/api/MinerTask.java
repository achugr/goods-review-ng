package ru.goodsreview.api;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 27.09.12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.api.grabber.ReviewGrabber;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.scheduler.SchedulerTask;
import ru.goodsreview.scheduler.TaskResult;
import ru.goodsreview.scheduler.context.Context;

import java.util.List;

public class MinerTask implements SchedulerTask {

    private EntityService entityService;

    @Required
    public void setEntityService(final EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public TaskResult run(final Context context) {

        ReviewGrabber reviewGrabber = new ReviewGrabber();

        List<JSONObject> reviews = reviewGrabber.grabAllReviews();

        entityService.writeEntities(reviews);

        return TaskResult.ok();
    }
}