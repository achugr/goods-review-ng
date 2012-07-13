package ru.goodsreview.api.provider;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 19:30
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public enum ResourceType {

    CATEGORY(1, "category", 150),
    MODEL(2, "model", 170),
    OFFER(3, "offer", 125),
    OPINION(4, "opinion", 170),
    SHOP(5, "shop", 150),
    GEOREGION(6, "georegion", 125),
    VENDOR(7, "vendor", 125),
    SEARCH(8, "search", 125),
    FILTER(9, "filter", 170);

    private final int typeId;
    private final String resourceType;
    private final int maxTimeout;

    ResourceType(final int typeId, final String resourceType, final int timeout) {
        this.typeId = typeId;
        this.resourceType = resourceType;
        this.maxTimeout = timeout;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public int getMaxTimeout() {
        return maxTimeout;
    }

    public int getTypeId() {
        return typeId;
    }
}
