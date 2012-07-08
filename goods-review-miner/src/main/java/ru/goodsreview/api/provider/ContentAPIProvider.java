package ru.goodsreview.api.provider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ru.goodsreview.api.request.builder.UrlRequest;
import ru.goodsreview.core.util.JSONUtil;
import ru.goodsreview.core.util.StringUtil;

/**
 * Artemij Chugreev
 * Date: 16.06.12
 * Time: 21:04
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class ContentAPIProvider {
    private Properties secureProperties;
    private final static Logger log = Logger.getLogger(ContentAPIProvider.class);

    private final HttpClient httpClient;
    private final GetMethod getMethod;
    private long lastQueryTime = 0;

    public void setSecureProperties(Properties secureProperties) {
        this.secureProperties = secureProperties;
    }

    public ContentAPIProvider() {
        this.httpClient = new HttpClient();
        this.getMethod = new GetMethod();
        this.getMethod.addRequestHeader("Authorization", secureProperties.get("api_key").toString());
    }

    public JSONObject provide(final UrlRequest urlRequest) {
//       timeout
        if (System.currentTimeMillis() - lastQueryTime < urlRequest.getResourceType().getMaxTimeout()) {
            try {
                Thread.sleep(urlRequest.getResourceType().getMaxTimeout());
            } catch (InterruptedException e) {
                log.error("some error with threads", e);
            }
            lastQueryTime = System.currentTimeMillis();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            getMethod.setURI(new URI(urlRequest.getUrl()));
            httpClient.executeMethod(getMethod);
            String json = StringUtil.inputStreamToString(getMethod.getResponseBodyAsStream());
            System.out.println("json string: " + json);
            jsonObject = new JSONObject(json);
            jsonObject.put("contentType", urlRequest.getResourceType().getName());
        } catch (IOException e) {
            log.error("probably, can't execute http get method", e);
        } catch (JSONException e) {
            log.error("some problems with json", e);
        }
        return jsonObject;
    }

    public List<JSONObject> provideAsArray(final UrlRequest urlRequest, final String[] parents, final String key) {
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
        }
        return new ArrayList<JSONObject>();
    }

}

