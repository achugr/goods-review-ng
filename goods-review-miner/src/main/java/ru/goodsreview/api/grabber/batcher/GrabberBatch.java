package ru.goodsreview.api.grabber.batcher;

import org.json.JSONObject;
import ru.goodsreview.core.util.Batch;

import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */

//        usage example:
//      Batch modelBatch = new GrabberBatch(EntityType.MODEL...);
//      crawl models and to this:
//      modelBatch.submit(modelJsonObject);
//
public class GrabberBatch<T> extends Batch <T>{

    private String entityType;

    public GrabberBatch(String entityType){
        this.entityType = entityType;
    }

    @Override
    public void handle(List<T> list) {
//        TODO store list of objects in accordance with entityType and update strategy
    }
}
