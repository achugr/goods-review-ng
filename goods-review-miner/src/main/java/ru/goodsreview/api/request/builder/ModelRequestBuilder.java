package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 1:16
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class ModelRequestBuilder extends RequestBuilder {

    public UrlRequest requestForModelById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.MODEL.getResourceType(), String.valueOf(id));
    }

    public UrlRequest requestForDetailsOnModelById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.MODEL.getResourceType(), String.valueOf(id), Resource.DETAILS.getName());
    }

    public UrlRequest requestForOffersOnModelById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.MODEL.getResourceType(), String.valueOf(id), Resource.OFFERS.getName());
    }

    public UrlRequest requestForOutletsOnModelById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.MODEL.getResourceType(), String.valueOf(id), Resource.MODEL_OUTLETS.getName());
    }

    private UrlRequest build(final Map<String, String> parameters, final String... resources) {
        return super.build(parameters, ResourceType.MODEL, resources);
    }

}
