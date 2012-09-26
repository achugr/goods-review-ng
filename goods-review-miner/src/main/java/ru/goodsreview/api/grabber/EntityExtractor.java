package ru.goodsreview.api.grabber;

import org.json.JSONObject;
import ru.goodsreview.core.db.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 26.09.12
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class EntityExtractor implements Visitor<JSONObject> {
    private List<JSONObject> entities = new ArrayList<JSONObject>();

    public List<JSONObject> getEntities(){
        return entities;
    }

    @Override
    public void visit(JSONObject jsonObject) {
        entities.add(jsonObject);
    }
}
