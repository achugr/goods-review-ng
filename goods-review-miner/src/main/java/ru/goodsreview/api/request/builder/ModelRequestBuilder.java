package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 1:16
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class ModelRequestBuilder extends RequestBuilder {

    //TODO final
    public UrlRequest requestForModelById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id)},
                parameters);
    }
    //TODO final

    public UrlRequest requestForDetailsOnModelById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.DETAILS.getName()},
                parameters);
    }

    //TODO final
    public UrlRequest requestForOffersOnModelById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.OFFERS.getName()},
                parameters);
    }

    //TODO final
    public UrlRequest requestForOutletsOnModelById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.MODEL_OUTLETS.getName()},
                parameters);
    }

    //TODO final
    private UrlRequest build(String [] resources,  Map<String, String> parameters){
        return super.build(resources, parameters, ResourceType.MODEL);
    }

}
