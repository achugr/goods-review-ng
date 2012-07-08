package ru.goodsreview.api.request.builder;

import org.junit.Assert;
import org.junit.Test;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.HashMap;

/**
 * achugr, achugr@yandex-team.ru
 * 06.07.12
 */
//TODO нах наследование?
public class ModelRequestBuilderTest extends Assert {

    @Test
    public void requestForModelById(){
        ModelRequestBuilder modelRequestBuilder = new ModelRequestBuilder();
        UrlRequest urlRequest = modelRequestBuilder.requestForModelById(123, new HashMap<String, String>());
        Assert.assertEquals(urlRequest.getUrl(),
                "https://api.content.market.yandex.ru/v1/model/123.json?geo_id=0");
    }
}
