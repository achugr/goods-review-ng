package ru.goodsreview.api.grabber;

import org.json.JSONObject;
import ru.goodsreview.core.db.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
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
