package ru.goodsreview.api.url.generator;

import ru.goodsreview.api.provider.APISettings;
import ru.goodsreview.api.provider.ResourceType;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 16:58
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class RequestBuilder {

    public UrlRequest build(String[] resources, Map<String, String> parameters, ResourceType resourceType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(APISettings.MAIN_API_URL);
        for (String resource : resources) {
            stringBuilder.append("/").append(resource);
        }
        stringBuilder.append(APISettings.RESPONSE_FORMAT);
        stringBuilder.append("?");

        appendDefaultParameters(stringBuilder);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            stringBuilder.append("&");
        }
        return new UrlRequest(stringBuilder.toString(), resourceType);
    }

    private static void appendDefaultParameters(StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : APISettings.DEFAULT_PARAMETERS.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            stringBuilder.append("&");
        }
    }
}
