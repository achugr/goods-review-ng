package ru.goodsreview.api.provider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.core.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
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

    private final HttpClient httpClient;
    private final GetMethod getMethod;
    private long lastQueryTime = 0;

    public ContentAPIProvider() {
        this.httpClient = new HttpClient();
        this.getMethod = new GetMethod();
        this.getMethod.addRequestHeader("Authorization", APIProperties.API_KEY);
    }

    public JSONObject provide(UrlRequest urlRequest) {
//       timeout
        if (System.currentTimeMillis() - lastQueryTime < APIProperties.TIMEOUT) {
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

            //TODO нахрена ловить столько эксепшенов? достаточно словиьт отлько два в данном случа, все равно от комментария пользы ноль
        } catch (URIException e) {
            log.error("some problems with URI", e);
        } catch (HttpException e) {
            log.error("some problems with http", e);
        } catch (IOException e) {
            log.error("probably, can't execute http get method", e);
        } catch (JSONException e) {
            log.error("some problems with json", e);
        }
        return jsonObject;
    }

    public List<JSONObject> provideAsArray(UrlRequest urlRequest, String[] parents, String key) {
        JSONObject mainObject = provide(urlRequest);
        try {
//            go down by json to needed key
            for(String parent : parents){
                mainObject = mainObject.getJSONObject(parent);
            }
//            get JSONArray by specified key and cast it to List<JSONObject>
            return castJSONArrayToList(mainObject.getJSONArray(key));
        } catch (JSONException e) {
            //TODO логирование
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //TODO вот такое возвращать совсем нельзя, надо либо пустой список, либо выкидывать эксепшен
        return null;
    }

    //TODO надо оформить в виде отдельного класса в core и с адекватным названием метода
    private static List<JSONObject> castJSONArrayToList(JSONArray jsonArray) {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.optJSONObject(i));
        }
        return jsonObjects;
    }


}
