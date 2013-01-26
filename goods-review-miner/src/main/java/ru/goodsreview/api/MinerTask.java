package ru.goodsreview.api;

/**
 * User: 123
 * Date: 27.09.12
 * Time: 14:25
 */

import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.api.grabber.ReviewGrabber;
import ru.goodsreview.scheduler.SchedulerTask;
import ru.goodsreview.scheduler.TaskResult;
import ru.goodsreview.scheduler.context.Context;

public class MinerTask implements SchedulerTask {

    private ReviewGrabber reviewGrabber;

    @Required
    public void setReviewGrabber(ReviewGrabber reviewGrabber){
        this.reviewGrabber = reviewGrabber;
    }

    @Override
    public TaskResult run(final Context context) {

        reviewGrabber.grabAllReviews();

        return TaskResult.ok();
    }
}