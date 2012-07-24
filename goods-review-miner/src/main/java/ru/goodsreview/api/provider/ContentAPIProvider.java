package ru.goodsreview.api.provider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.api.request.builder.UrlRequest;
import ru.goodsreview.core.util.JSONUtil;

import java.io.IOException;
import java.util.List;

/**
 * Artemij Chugreev
 * Date: 16.06.12
 * Time: 21:04
 * email: achugr@yandex-team.ru
 * skype: achugr
 */

public class ContentAPIProvider {
    private final static Logger log = Logger.getLogger(ContentAPIProvider.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final int HTTP_OK = 200;
    private static final String TYPE_ID = "typeId";

    private String apiKey;

    private final HttpClient httpClient;
    private long lastQueryTime = 0;

    public ContentAPIProvider() {
        this.httpClient = new HttpClient();
    }

    @Required
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public JSONObject provide(final UrlRequest urlRequest) {
        final GetMethod getMethod = new GetMethod(urlRequest.getUrl());
        getMethod.addRequestHeader(AUTHORIZATION_HEADER, apiKey);
//       timeout
        if (System.currentTimeMillis() - lastQueryTime < urlRequest.getResourceType().getMaxTimeout()) {
            try {
                Thread.sleep(urlRequest.getResourceType().getMaxTimeout());
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            lastQueryTime = System.currentTimeMillis();
        }

        try {
            httpClient.executeMethod(getMethod);
            final JSONObject jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
//            if http status code isn't 202 => something wrong
            if(getMethod.getStatusCode() != HTTP_OK){
                log.error(jsonObject.toString());
                return new JSONObject();
            }

            jsonObject.put(TYPE_ID, urlRequest.getResourceType().getTypeId());
            return jsonObject;
        } catch (IOException e) {
            log.error("probably, can't execute http get method", e);
            return new JSONObject();
        } catch (JSONException e) {
            try {
                log.error("some problems with json, response: " + getMethod.getResponseBodyAsString(), e);
            } catch (IOException e1) {
                log.error(e.getMessage(), e);
            }
            throw new RuntimeException(e);
        }
    }

    public List<JSONObject> provideAsList(final UrlRequest urlRequest, final String key, final String... parents) {
        JSONObject mainObject = provide(urlRequest);
        try {
//            go down by json to needed key
            for (String parent : parents) {
                mainObject = mainObject.getJSONObject(parent);
            }
//            get JSONArray by specified key and cast it to List<JSONObject>
            return JSONUtil.convertJSONArrayToListOfJSONObjects(mainObject.getJSONArray(key));
        } catch (JSONException e) {
            log.error("some problems with json", e);
            throw new RuntimeException(

            );
        }
    }

}

