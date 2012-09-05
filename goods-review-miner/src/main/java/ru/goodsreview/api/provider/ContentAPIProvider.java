package ru.goodsreview.api.provider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.api.request.builder.UrlRequest;

import java.io.IOException;

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
    private static final String TYPE_ID = "typeId";
    private static final int TIME_OUT = 500;

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

    public JSONObject provide(final UrlRequest urlRequest) throws JSONException, IOException {
        GetMethod getMethod = new GetMethod(urlRequest.getUrl());
        getMethod.addRequestHeader(AUTHORIZATION_HEADER, apiKey);
//       timeout
        try {
            Thread.sleep(TIME_OUT);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        /*if (System.currentTimeMillis() - lastQueryTime < urlRequest.getResourceType().getMaxTimeout()) {
            try {
                Thread.sleep(urlRequest.getResourceType().getMaxTimeout());
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            lastQueryTime = System.currentTimeMillis();
        }*/

        httpClient.executeMethod(getMethod);
        if(getMethod.getStatusCode() == HttpStatus.SC_OK){
            String response = getMethod.getResponseBodyAsString();
            JSONObject jsonObject = new JSONObject(response);
            jsonObject.put(TYPE_ID, urlRequest.getResourceType().getTypeId());
            log.debug("Http status : " + getMethod.getStatusCode() + "\n " +
                      "JSON object : " + jsonObject.toString());
            return jsonObject;
        }else{
            throw new HttpException("Bad response. " +
                    "Http status : " + getMethod.getStatusCode() + "\n" +
                    "Request url : " + urlRequest.getUrl());
        }
    }
}

