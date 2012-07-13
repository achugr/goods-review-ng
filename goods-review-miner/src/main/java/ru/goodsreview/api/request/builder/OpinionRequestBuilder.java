package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 20:29
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class OpinionRequestBuilder extends RequestBuilder {

    public UrlRequest requestForOpinionOnShopById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.SHOP.getResourceType(), String.valueOf(id), Resource.SHOP_OPINION.getName());
    }

    public UrlRequest requestForOpinionOnModelById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.MODEL.getResourceType(), String.valueOf(id), Resource.MODEL_OPINION.getName());
    }

    private UrlRequest build(final Map<String, String> parameters, final String... resources) {
        return super.build(parameters, ResourceType.OPINION, resources);
    }
}
