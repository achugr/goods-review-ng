package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 20:34
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class ShopRequestBuilder extends RequestBuilder {
    //TODO final

    public UrlRequest requestForShopById(long id, Map<String, String> parameters){
        return build(new String[]{ResourceType.SHOP.getName(), String.valueOf(id)},
                parameters);
    }
    //TODO final

    public UrlRequest requestForOutletsOfShopById(long id, Map<String, String> parameters){
        return build(new String[]{ResourceType.SHOP.getName(), String.valueOf(id), Resource.SHOP_OUTLETS.getName()},
                parameters);
    }
    //TODO final

    private UrlRequest build(String [] resources,  Map<String, String> parameters){
        return super.build(resources, parameters, ResourceType.SHOP);
    }
}
