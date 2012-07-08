package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.ResourceType;

/**
 * Artemij Chugreev
 * Date: 18.06.12
 * Time: 19:58
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class UrlRequest {
    private final String url;
    private final ResourceType resourceType;

    public UrlRequest(final String url, final ResourceType resourceType) {
        this.url = url;
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

}
