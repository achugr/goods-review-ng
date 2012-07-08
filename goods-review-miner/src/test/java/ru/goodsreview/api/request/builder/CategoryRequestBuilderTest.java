package ru.goodsreview.api.request.builder;

import org.junit.Assert;
import org.junit.Test;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * achugr, achugr@yandex-team.ru
 * 06.07.12
 *
 *TODO где final? в остальных местах такой же вопрос
 */
public class CategoryRequestBuilderTest {

    @Test
    public void requestForListOfCategoriesTest(){
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
        Assert.assertEquals(urlRequest.getUrl(),
                "https://api.content.market.yandex.ru/v1/category.json?geo_id=0");
    }

    @Test
    public void requestForListOfModelsOfCategoryByIdTest(){
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        Map<String, String> params = new HashMap<String, String>();
        params.put("vendor_id", "1");
        params.put("sort", "price");
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfModelsOfCategoryById(123, params);
        Assert.assertEquals(urlRequest.getUrl(),
                "https://api.content.market.yandex.ru/v1/category/123/models.json?geo_id=0&sort=price&vendor_id=1");
    }

}
