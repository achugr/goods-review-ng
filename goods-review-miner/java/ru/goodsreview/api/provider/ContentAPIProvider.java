package ru.goodsreview.api.provider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Artemij Chugreev
 * Date: 16.06.12
 * Time: 21:04
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class ContentAPIProvider {
    private final HttpClient httpClient;
    private final GetMethod getMethod;
    private long lastQueryTime = 0;

    public ContentAPIProvider() {
        this.httpClient = new HttpClient();
        this.getMethod = new GetMethod();
        this.getMethod.addRequestHeader("Authorization", APISettings.API_KEY);
    }

    public JSONObject provide(UrlRequest urlRequest) {
//       timeout
        if (System.currentTimeMillis() - lastQueryTime < APISettings.TIMEOUT) {
            try {
                Thread.sleep(urlRequest.getResourceType().getMaxTimeout());
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

        } catch (URIException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (HttpException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return jsonObject;
    }

    public List<JSONObject> provideAsArray(UrlRequest urlRequest, String[] parents, String key) {
        JSONObject mainObject = provide(urlRequest);
        try {
            for(String parent : parents){
                mainObject = mainObject.getJSONObject(parent);
            }
            return castJSONArrayToList(mainObject.getJSONArray(key));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private static List<JSONObject> castJSONArrayToList(JSONArray jsonArray) {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.optJSONObject(i));
        }
        return jsonObjects;
    }


}
