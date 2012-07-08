package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 20:29
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class OpinionRequestBuilder extends RequestBuilder{

    public UrlRequest requestForOpinionOnShopById(long id, Map<String, String> parameters){
        return build(new String[]{ResourceType.SHOP.getName(), String.valueOf(id), Resource.SHOP_OPINION.getName()},
                parameters);
    }

    public UrlRequest requestForOpinionOnModelById(long id, Map<String, String> parameters){
        return build(new String[]{ResourceType.MODEL.getName(), String.valueOf(id), Resource.MODEL_OPINION.getName()},
                parameters);
    }

    private UrlRequest build(String [] resources,  Map<String, String> parameters){
        return super.build(resources, parameters, ResourceType.OPINION);
    }
}
