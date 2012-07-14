package ru.goodsreview.api.grabber.batch;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.core.util.Batch;

import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */

public class GrabberBatch<T> extends Batch <T>{

    private final static Logger log = Logger.getLogger(GrabberBatch.class);

//    TODO честно говоря, пока что я не вижу смысла наследоваться от Batch, тк вот такой метод
//    можно написать и в самом классе Batch и не переопределять его
//    поскольку стратегию обновления все равно на EntityService возлагаем
    @Override
    public void handle(List<T> list) {
        if(list.size() < 1){
            return;
        }
//        TODO store list of objects in accordance with entityType and update strategy
        EntityService entityService = new EntityService();
        if(! (list.get(0) instanceof JSONObject)){
            log.error("element of list must be instance of JSONObject");
            throw new IllegalArgumentException("element of list must be instance of JSONObject");
        }
        @SuppressWarnings("unchecked")
        List<JSONObject> entities = (List<JSONObject>) list;
        entityService.writeEntities(entities);
    }
}
