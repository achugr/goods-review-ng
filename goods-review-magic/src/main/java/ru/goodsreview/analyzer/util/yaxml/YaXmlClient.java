package ru.goodsreview.analyzer.util.yaxml;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: ilya
 * Date: 24.01.13
 */
public class YaXmlClient {
    private static HttpClient client = new DefaultHttpClient();
    static int routerPort = 8000;
    public static String defaultHttp = "http://134.0.115.143:";


    public String perform(String command) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("command", command));
        try {

            HttpPost post1 = new HttpPost(defaultHttp + routerPort + "/");
            post1.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            client = new DefaultHttpClient();
            HttpResponse response = client.execute(post1);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                return line;
            }
        } catch (HttpHostConnectException e) {
            throw new RuntimeException("Server is unavailable.", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public String extractData(String request, Pattern pattern) {
        final String response = perform(request);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "not found";
        }
    }

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(System.out, true);

        String request = "яндекс xml";

        YaXmlClient yaXml = new YaXmlClient();
        System.out.println(yaXml.perform("\"ноутбук отличный\""));
//        System.out.println(yaXml.extractData("\"ноутбук отличный\" host:market.yandex.ru", Pattern.compile("<found-docs priority=\"all\">(\\d*)</found-docs>")));

    }

}