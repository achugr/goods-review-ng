package ru.goodsreview.api.grabber;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
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
    MODEL_OPINIONS("modelOpinions"),
    TOTAL("total");

    private final String key;

    JSONKeys(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
}
