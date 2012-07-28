package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.ResourceType;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 16:58
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public abstract class RequestBuilder {
    private static final String API_VERSION = "v1";
    private static final String RESPONSE_FORMAT = ".json";
    private static final String MAIN_API_URL = "https://api.content.market.yandex.ru/" + API_VERSION;
    private String geoIdValue = "0";

//    @Required
//    public void setGeoIdValue(String geoIdValue) {
//        this.geoIdValue = geoIdValue;
//    }

    public UrlRequest build(final Map<String, String> parameters, final ResourceType resourceType, final String... resources) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MAIN_API_URL);
        for (final String resource : resources) {
            stringBuilder.append("/").append(resource);
        }
        stringBuilder.append(RESPONSE_FORMAT);
        stringBuilder.append("?");

        stringBuilder.append(RequestParams.GEO_ID.getKey()).append("=").append(geoIdValue);

        for (final Map.Entry<String, String> entry : parameters.entrySet()) {
            stringBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
//        delete last "&" symbol
//        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return new UrlRequest(stringBuilder.toString(), resourceType);
    }

}