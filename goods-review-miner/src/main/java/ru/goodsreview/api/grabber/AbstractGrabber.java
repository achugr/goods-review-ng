package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.api.grabber.batch.GrabberBatch;
import ru.goodsreview.api.provider.ContentAPIProvider;
import ru.goodsreview.core.db.entity.EntityType;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 24.08.12
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGrabber {
    private final static Logger log = Logger.getLogger(AbstractGrabber.class);

    protected EntityType entityType;
    protected ContentAPIProvider contentApiProvider;
    protected GrabberBatch grabberBatch;

    @Required
    public void setGrabberBatch(GrabberBatch grabberBatch){
        this.grabberBatch = grabberBatch;
    }

    @Required
    public GrabberBatch getGrabberBatch(){
        return grabberBatch;
    }

    @Required
    public ContentAPIProvider getContentApiProvider() {
        return contentApiProvider;
    }

    @Required
    public void setContentApiProvider(ContentAPIProvider contentApiProvider) {
        this.contentApiProvider = contentApiProvider;
    }

    protected void batchEntityList(List<JSONObject> entityList){
        for(JSONObject entity : entityList) {
            grabberBatch.submit(entity);
        }
        grabberBatch.flush();
    }

    protected void setTypeId(List<JSONObject> entityList){
        for(JSONObject entity : entityList){
            try {
                entity.put("typeId", entityType.getTypeId());
            } catch (JSONException e) {
                log.error("Error in entity type setting");
                throw new RuntimeException();
            }
        }
    }

    protected void processEntityList(List<JSONObject> entityList){
        log.info("Adding entity to DB started");
        setTypeId(entityList);
        batchEntityList(entityList);
        log.info("Adding entity to DB ended");
    }
}
