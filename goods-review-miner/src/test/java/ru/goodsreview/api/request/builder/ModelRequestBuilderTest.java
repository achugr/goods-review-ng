package ru.goodsreview.api.request.builder;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * achugr, achugr@yandex-team.ru
 * 06.07.12
 */
public class ModelRequestBuilderTest {

    @Test
    public void requestForModelById() {
        final ModelRequestBuilder modelRequestBuilder = new ModelRequestBuilder();
        final UrlRequest urlRequest = modelRequestBuilder.requestForModelById(123, new HashMap<String, String>());
        Assert.assertEquals(urlRequest.getUrl(),
                "https://api.content.market.yandex.ru/v1/model/123.json?geo_id=0");
    }
}
