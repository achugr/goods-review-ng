package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 20:34
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class ShopRequestBuilder extends RequestBuilder {

    public UrlRequest requestForShopById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.SHOP.getResourceType(), String.valueOf(id));
    }

    public UrlRequest requestForOutletsOfShopById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.SHOP.getResourceType(), String.valueOf(id), Resource.SHOP_OUTLETS.getName());
    }

    private UrlRequest build(final Map<String, String> parameters, final String... resources) {
        return super.build(parameters, ResourceType.SHOP, resources);
    }
}
