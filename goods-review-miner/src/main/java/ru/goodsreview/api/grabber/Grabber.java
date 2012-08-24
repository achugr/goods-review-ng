package ru.goodsreview.api.grabber;

import org.json.JSONObject;
import ru.goodsreview.api.grabber.batch.GrabberBatch;
import ru.goodsreview.core.db.entity.EntityService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 24.08.12
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class Grabber {

    private final GrabberBatch grabberBatcher = new GrabberBatch(new EntityService());

    protected void batchList(List<JSONObject> itemList){
        for(JSONObject item : itemList){
            grabberBatcher.submit(item);
        }
    }
}
