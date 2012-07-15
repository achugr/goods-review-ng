package ru.goodsreview.core.model.impl.json;

import org.json.JSONObject;
import ru.goodsreview.core.model.Model;

import java.util.List;

import static ru.goodsreview.core.util.JSONUtil.*;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 15:14
 */
public class ModelOverJson implements Model {

    protected final JSONObject jsonObject;

    public ModelOverJson(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public long getCategoryId() {
        return Long.parseLong(unsafeGetString(jsonObject, "categoryId"));
    }

    @Override
    public long getId() {
        return Long.parseLong(unsafeGetString(jsonObject, "id"));
    }

    @Override
    public List<String> getPhotos() {
        return unsafeJsonArrayToStringList(unsafeGetJsonArray(unsafeGetJsonObject(jsonObject, "photos"), "photo"));

    }

    @Override
    public String getMainPhoto() {
        return unsafeGetString(jsonObject, "mainPhoto");
    }

    @Override
    public String getLink() {
        return unsafeGetString(jsonObject, "link");
    }

    @Override
    public String getName() {
        return unsafeGetString(jsonObject, "name");
    }

    @Override
    public String getDescription() {
        return unsafeGetString(jsonObject, "description");
    }

    @Override
    public boolean isGroup() {
        return Boolean.parseBoolean(unsafeGetString(jsonObject, "isGroup"));
    }

    @Override
    public boolean isNew() {
        return Boolean.parseBoolean(unsafeGetString(jsonObject, "isNew"));
    }

    @Override
    public int getReviewsCount() {
        return Integer.parseInt(unsafeGetString(jsonObject, "reviewsCount"));
    }

    @Override
    public double getRating() {
        return Double.parseDouble(unsafeGetString(jsonObject, "reviewsCount"));
    }

    @Override
    public String getVendor() {
        return unsafeGetString(jsonObject, "vendor");
    }

    @Override
    public long getVendorId() {
        return Long.parseLong(unsafeGetString(jsonObject, "vendorId"));
    }
}
