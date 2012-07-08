package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;

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
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id)},
                parameters);
    }

    public UrlRequest requestForDetailsOnModelById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.DETAILS.getName()},
                parameters);
    }

    public UrlRequest requestForOffersOnModelById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.OFFERS.getName()},
                parameters);
    }

    public UrlRequest requestForOutletsOnModelById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.MODEL_OUTLETS.getName()},
                parameters);
    }

    private UrlRequest build(final String[] resources, final Map<String, String> parameters) {
        return super.build(resources, parameters, ResourceType.MODEL);
    }

}
