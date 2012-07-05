package ru.goodsreview.api.url.generator;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;
import ru.goodsreview.api.provider.UrlRequest;

import java.util.Map;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 15:04
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public class CategoryRequestBuilder extends RequestBuilder {

    public UrlRequest requestForListOfCategories(Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName()},
                parameters);
    }

    public UrlRequest requestForInfoAboutCategoryById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id)},
                parameters);
    }

    public UrlRequest requestForListOfChildrenCategoriesById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.CATEGORY_CHILDREN.getName()},
                parameters);
    }

    public UrlRequest requestForListOfModelsOfCategoryById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.MODELS.getName()},
                parameters);
    }

    public UrlRequest requestForListOfFiltersOfCategoryById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.FILTERS.getName()},
                parameters);
    }

    private UrlRequest build(String [] resources,  Map<String, String> parameters){
        return super.build(resources, parameters, ResourceType.CATEGORY);
    }

}
