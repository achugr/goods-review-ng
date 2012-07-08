package ru.goodsreview.api.provider;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 19:30
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public enum ResourceType {

    CATEGORY("category", 150),
    MODEL("model", 170),
    OFFER("offer", 125),
    OPINION("opinion", 170),
    SHOP("shop", 150),
    GEOREGION("georegion", 125),
    VENDOR("vendor", 125),
    SEARCH("search", 125),
    FILTER("filter", 170);

    private String resourceType;
    private int maxTimeout;

    ResourceType(String resourceType, int timeout){
        this.resourceType = resourceType;
        this.maxTimeout = timeout;
    }

    public String getName(){
        return this.resourceType;
    }

    public int getMaxTimeout(){
        return maxTimeout;
    }
}
