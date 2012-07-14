package ru.goodsreview.core.model.impl;

import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.model.Category;

import static ru.goodsreview.core.util.JSONUtil.unsafeGetString;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 17:11
 */
public class CategoryOverJson extends OverJsonImpl implements Category {

    public CategoryOverJson(final JSONObject jsonObject) {
        super(jsonObject, EntityType.MODEL);
    }

    @Override
    public int getModelsNum() {
        return Integer.parseInt(unsafeGetString(jsonObject, "modelsNum"));
    }

    @Override
    public int getOffersNum() {
        return Integer.parseInt(unsafeGetString(jsonObject, "offersNum"));
    }

    @Override
    public int getChildrenCount() {
        return Integer.parseInt(unsafeGetString(jsonObject, "childrenCount"));
    }

    @Override
    public String getName() {
        return unsafeGetString(jsonObject, "name");
    }

    @Override
    public long getParentId() {
        return Long.parseLong(unsafeGetString(jsonObject, "parentId"));
    }

    @Override
    public long getId() {
        return Long.parseLong(unsafeGetString(jsonObject, "id"));
    }
}
