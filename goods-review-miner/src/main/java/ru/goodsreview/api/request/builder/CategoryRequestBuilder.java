package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 15:04
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class CategoryRequestBuilder extends RequestBuilder {

    public UrlRequest requestForListOfCategories(final Map<String, String> parameters) {
        return build(parameters, ResourceType.CATEGORY.getResourceType());
    }

    public UrlRequest requestForInfoAboutCategoryById(final long id) {
        return build(new HashMap<String, String>(), ResourceType.CATEGORY.getResourceType(), String.valueOf(id));
    }

    public UrlRequest requestForListOfChildrenCategoriesById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.CATEGORY.getResourceType(), String.valueOf(id), Resource.CATEGORY_CHILDREN.getName());
    }

    public UrlRequest requestForListOfModelsOfCategoryById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.CATEGORY.getResourceType(), String.valueOf(id), Resource.MODELS.getName());
    }

    public UrlRequest requestForListOfFiltersOfCategoryById(final long id, final Map<String, String> parameters) {
        return build(parameters, ResourceType.CATEGORY.getResourceType(), String.valueOf(id), Resource.FILTERS.getName());
    }

    private UrlRequest build(final Map<String, String> parameters, final String... resources) {
        return super.build(parameters, ResourceType.CATEGORY, resources);
    }

}
