package ru.goodsreview.api.provider;

/**
 * Artemij Chugreev
 * Date: 16.06.12
 * Time: 21:16
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public enum Resource {

    CATEGORY_CHILDREN("children"),
    MODELS("models"),
    DETAILS("details"),
    OFFERS("offers"),
    SHOP_OUTLETS("outlets"),
    MODEL_OUTLETS("outlets"),
    SHOP_OPINION("opinion"),
    MODEL_OPINION("opinion"),
    SUGGEST("suggest"),
    FILTERS("filters");

    private final String resourceName;

    Resource(final String resourceName) {
        this.resourceName = resourceName;
    }

    public String getName() {
        return resourceName;
    }

}
