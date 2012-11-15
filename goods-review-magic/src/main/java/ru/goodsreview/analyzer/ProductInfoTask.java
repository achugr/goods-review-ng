package ru.goodsreview.analyzer;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.db.visitor.Visitor;
import ru.goodsreview.core.util.Batch;
import ru.goodsreview.scheduler.SchedulerTask;
import ru.goodsreview.scheduler.TaskResult;
import ru.goodsreview.scheduler.context.Context;

import java.util.List;

/**
 * User: ilya
 * Date: 16.11.12
 */
public class ProductInfoTask implements SchedulerTask {


    @Override
    public TaskResult run(final Context context) {

        return TaskResult.ok();
    }

    protected JSONObject process(final JSONObject object){
        return null;
    }
}