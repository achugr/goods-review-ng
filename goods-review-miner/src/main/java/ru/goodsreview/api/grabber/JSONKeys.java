package ru.goodsreview.api.grabber;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 03.08.12
 * Time: 4:06
 * To change this template use File | Settings | File Templates.
 */
public enum JSONKeys {

    CATEGORIES("categories"),
    MODELS("models"),

    ID("id"),
    NAME("name"),
    ITEMS("items"),
    CHILDREN_COUNT("childrenCount"),
    MODELS_NUM("modelsNum"),
    REVIEWS_COUNT("reviewsCount"),
    OPINION("opinion"),
    MODEL_OPINIONS("modelOpinions");

    private final String key;

    JSONKeys(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
}
