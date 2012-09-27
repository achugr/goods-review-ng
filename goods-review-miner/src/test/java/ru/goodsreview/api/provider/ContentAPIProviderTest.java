package ru.goodsreview.api.provider;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.ModelRequestBuilder;
import ru.goodsreview.api.request.builder.UrlRequest;

import java.io.IOException;
import java.util.HashMap;

/**
 * achugr, achugr@yandex-team.ru
 * 06.07.12
 */

/**
 * class for analyzer ContentApiProvider
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/content-api-provider-test-bean.xml")
public class ContentAPIProviderTest {
    private final static Logger log = Logger.getLogger(ContentAPIProviderTest.class);

    @Autowired
    private ContentAPIProvider contentApiProvider;

    @Test
    public void isValidAnswerTest() throws IOException, JSONException {
        ModelRequestBuilder modelRequestBuilder = new ModelRequestBuilder();
        UrlRequest urlRequest = modelRequestBuilder.requestForModelById(7722505, new HashMap<String, String>());
        JSONObject result = contentApiProvider.provide(urlRequest);
//        provide method returns full jsonObject, empty jsonObject or throws RuntimeException
        Assert.assertNotSame(result.toString(), "");
    }

    @Test
    public void isValidAnswerTestOnCategoryRequest() throws IOException, JSONException {
        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();
        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfCategories(new HashMap<String, String>());
        JSONObject result = contentApiProvider.provide(urlRequest);
//        System.out.println(result.toString());
//        provide method returns full jsonObject, empty jsonObject or throws RuntimeException
        Assert.assertNotSame(result.toString(), "");
    }

}
