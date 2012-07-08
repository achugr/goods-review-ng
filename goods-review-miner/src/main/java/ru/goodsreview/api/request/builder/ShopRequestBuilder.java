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
        return build(new String[]{ResourceType.SHOP.getName(), String.valueOf(id)},
                parameters);
    }

    public UrlRequest requestForOutletsOfShopById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.SHOP.getName(), String.valueOf(id), Resource.SHOP_OUTLETS.getName()},
                parameters);
    }

    private UrlRequest build(final String[] resources, final Map<String, String> parameters) {
        return super.build(resources, parameters, ResourceType.SHOP);
    }
}
