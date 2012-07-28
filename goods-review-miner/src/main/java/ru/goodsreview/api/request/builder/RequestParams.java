package ru.goodsreview.api.request.builder;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 26.07.12
 * Time: 2:44
 * To change this template use File | Settings | File Templates.
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
