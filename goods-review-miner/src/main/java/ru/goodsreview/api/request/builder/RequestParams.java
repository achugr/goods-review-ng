package ru.goodsreview.api.request.builder;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 12:26
 */
public enum RequestParams {

    GEO_ID("geo_id"),
    SORT("sort"),
    PAGE("page"),
    COUNT("count");

    private final String param;

    RequestParams(String param){
        this.param = param;
    }

    public String getKey(){
        return this.param;
    }
}
