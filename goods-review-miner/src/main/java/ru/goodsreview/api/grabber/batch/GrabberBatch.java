package ru.goodsreview.api.grabber.batch;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityService;
import ru.goodsreview.core.util.Batch;

import java.util.List;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */

public class GrabberBatch extends Batch <JSONObject>{

    private final static Logger log = Logger.getLogger(GrabberBatch.class);

    private EntityService entityService;

    public GrabberBatch(EntityService entityService) {
        this.entityService = entityService;
    }

    //    TODO честно говоря, пока что я не вижу смысла наследоваться от Batch, тк вот такой метод
//    можно написать и в самом классе Batch и не переопределять его
//    поскольку стратегию обновления все равно на EntityService возлагаем
    @Override
    public void handle(List<JSONObject> list) {
        entityService.writeEntities(list);
    }
}
