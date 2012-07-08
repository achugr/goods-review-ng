package ru.goodsreview.api.provider;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 1:20
 * email: achugr@yandex-team.ru
 * skype: achugr
 *
 *
 * //TODO Это еще хуже, чем было
 * перенести все переменные в сеттеры отдельных классов, а все константы можно оставить
 */
@Deprecated
public final class APIProperties {
     private String name;

//    public static String API_KEY;
//    private static final String API_KEY_PATH = "goods-review-miner/api_key.txt";


//    static {
//        try {
//            API_KEY = new Scanner(new File(API_KEY_PATH)).nextLine();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
    @Required
    //TODO final
    public void setName(String name){
        this.name = name;
    }

    //TODO final
    public static void main(String[] args) {
        APIProperties apiProperties = new APIProperties();
//        apiProperties.properties.getProperty("name");
        System.out.println(apiProperties.name);
    }

    private static final String API_KEY_PATH = "goods-review-miner/api_key.txt";
    public static String API_KEY;
    public static final String API_VERSION = "v1";
    public static final String MAIN_API_URL = "https://api.content.market.yandex.ru/" + API_VERSION;
    public static final String GEO_ID_PARAM = "geo_id";
    public static final String GEO_ID_VALUE = "0";
    public static final String RESPONSE_FORMAT = ".json";
    public static final int TIMEOUT = 100;
    public static final Map<String, String> DEFAULT_PARAMETERS = new HashMap<String, String>();

    static {
        try {
            API_KEY = new Scanner(new File(API_KEY_PATH)).nextLine();
        } catch (FileNotFoundException e) {
            //TODO логирование
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DEFAULT_PARAMETERS.put(GEO_ID_PARAM, GEO_ID_VALUE);
    }

    private APIProperties(){}
}
