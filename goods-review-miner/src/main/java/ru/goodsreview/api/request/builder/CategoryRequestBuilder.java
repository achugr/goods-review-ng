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
        return build(new String[]{ResourceType.CATEGORY.getName()},
                parameters);
    }

    public UrlRequest requestForInfoAboutCategoryById(final long id) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id)},
                new HashMap<String, String>());
    }

    public UrlRequest requestForListOfChildrenCategoriesById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.CATEGORY_CHILDREN.getName()},
                parameters);
    }

    public UrlRequest requestForListOfModelsOfCategoryById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.MODELS.getName()},
                parameters);
    }

    public UrlRequest requestForListOfFiltersOfCategoryById(final long id, final Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.FILTERS.getName()},
                parameters);
    }

    private UrlRequest build(final String[] resources, final Map<String, String> parameters) {
        return super.build(resources, parameters, ResourceType.CATEGORY);
    }

}
