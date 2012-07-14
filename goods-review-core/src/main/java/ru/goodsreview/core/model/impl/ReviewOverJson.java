package ru.goodsreview.core.model.impl;

import org.json.JSONObject;
import ru.goodsreview.core.db.entity.EntityType;
import ru.goodsreview.core.model.Review;
import ru.goodsreview.core.util.DateUtil;

import java.util.Date;

import static ru.goodsreview.core.util.JSONUtil.unsafeGetString;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 14:39
 */
public class ReviewOverJson implements Review {

    private final JSONObject jsonObject;

    public ReviewOverJson(final JSONObject jsonObject) {
        if (EntityType.REVIEW.equals(EntityType.recognizeEntity(jsonObject))) {
            this.jsonObject = jsonObject;
        } else {
            throw new IllegalArgumentException("illegal inner json object");
        }
    }

    @Override
    public String getPro() {
        return unsafeGetString(jsonObject, "pro");
    }

    @Override
    public String getContra() {
        return unsafeGetString(jsonObject, "contra");
    }

    @Override
    public String getText() {
        return unsafeGetString(jsonObject, "text");
    }

    @Override
    public int getGrade() {
        return Integer.parseInt(unsafeGetString(jsonObject, "grade"));
    }

    @Override
    public int getAgree() {
        return Integer.parseInt(unsafeGetString(jsonObject, "agree"));
    }

    @Override
    public int getReject() {
        return Integer.parseInt(unsafeGetString(jsonObject, "reject"));
    }

    @Override
    public Date getDate() {
        return DateUtil.parseFromBaseFormat(unsafeGetString(jsonObject, "date"));
    }

    @Override
    public long getId() {
        return Long.parseLong(unsafeGetString(jsonObject, "id"));
    }

    @Override
    public long getModelId() {
        return Long.parseLong(unsafeGetString(jsonObject, "modelId"));
    }
}
