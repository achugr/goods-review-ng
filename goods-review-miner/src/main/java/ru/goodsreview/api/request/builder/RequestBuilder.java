package ru.goodsreview.api.request.builder;

import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.api.provider.ResourceType;

import java.util.Map;
import java.util.Properties;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 16:58
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class RequestBuilder {
    private static final String API_VERSION = "v1";
    private static final String RESPONSE_FORMAT = ".json";
    private static final String GEO_ID_PARAM = "geo_id";
    private static final String MAIN_API_URL = "https://api.content.market.yandex.ru/";
    private Properties properties;

    @Required
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public UrlRequest build(final String[] resources, final Map<String, String> parameters, final ResourceType resourceType) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MAIN_API_URL);
        for (String resource : resources) {
            stringBuilder.append("/").append(resource);
        }
        stringBuilder.append(properties.get("response_format"));
        stringBuilder.append("?");

        stringBuilder.append(GEO_ID_PARAM).append("=").append(properties.get("geo_id_value"));

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            stringBuilder.append("&");
        }
//        delete last "&" symbol
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return new UrlRequest(stringBuilder.toString(), resourceType);
    }

}