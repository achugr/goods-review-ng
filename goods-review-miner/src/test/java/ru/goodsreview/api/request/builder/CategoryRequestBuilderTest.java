package ru.goodsreview.api.request.builder;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * achugr, achugr@yandex-team.ru
 * 06.07.12
 */

public class CategoryRequestBuilderTest {

    @Test
    public void requestForListOfCategoriesTest() {
        final CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        final UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
        System.out.println(urlRequest.getUrl());
        Assert.assertEquals(urlRequest.getUrl(),
                "https://api.content.market.yandex.ru/v1/category.json?geo_id=0");
    }

    @Test
    public void requestForListOfModelsOfCategoryByIdTest() {
        final CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("vendor_id", "1");
        params.put("sort", "price");
        final UrlRequest urlRequest = categoryRequestBuilder.requestForListOfModelsOfCategoryById(123, params);
        Assert.assertEquals(urlRequest.getUrl(),
                "https://api.content.market.yandex.ru/v1/category/123/models.json?geo_id=0&sort=price&vendor_id=1");
    }

}
