package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.APIProperties;
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
        stringBuilder.append(APIProperties.MAIN_API_URL);
        for (String resource : resources) {
            stringBuilder.append("/").append(resource);
        }
        stringBuilder.append(APIProperties.RESPONSE_FORMAT);
        stringBuilder.append("?");

        appendDefaultParameters(stringBuilder);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            stringBuilder.append("&");
        }
//        delete last "&" symbol
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return new UrlRequest(stringBuilder.toString(), resourceType);
    }

    private static void appendDefaultParameters(StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : APIProperties.DEFAULT_PARAMETERS.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            stringBuilder.append("&");
        }
    }
}
