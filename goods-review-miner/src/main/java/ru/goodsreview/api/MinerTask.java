package ru.goodsreview.api;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 27.09.12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
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

    @Required
    public ReviewGrabber getReviewGrabber(){
        return reviewGrabber;
    }

    @Override
    public TaskResult run(final Context context) {

        reviewGrabber.grabAllReviews();

        return TaskResult.ok();
    }
}