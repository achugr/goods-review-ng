package ru.goodsreview.api.provider;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
 */

public final class APIProperties {
    private Properties properties;
    private Properties secureProperties;

    @Required
    public void setSecureProperties(Properties secureProperties) {
        this.secureProperties = secureProperties;
    }

    @Required
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public Properties getSecureProperties() {
        return secureProperties;
    }

//    public static void main(String[] args) {
//        BeanFactory factory = new FileSystemXmlApplicationContext("goods-review-miner/src/main/resources/beans.xml");
//        APIProperties apiProperties = (APIProperties) factory.getBean("properties");
//        System.out.println(apiProperties.properties.get("main_api_url"));
//    }

//    private static final String API_KEY_PATH = "goods-review-miner/api_key.txt";
//    public static String API_KEY;
//    public static final String API_VERSION = "v1";
//    public static final String MAIN_API_URL = "https://api.content.market.yandex.ru/" + API_VERSION;
//    public static final String GEO_ID_PARAM = "geo_id";
//    public static final String GEO_ID_VALUE = "0";
//    public static final String RESPONSE_FORMAT = ".json";
//    public static final int TIMEOUT = 100;
//    public static final Map<String, String> DEFAULT_PARAMETERS = new HashMap<String, String>();


}
