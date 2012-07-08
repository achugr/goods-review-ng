package ru.goodsreview.api.request.builder;

import ru.goodsreview.api.provider.Resource;
import ru.goodsreview.api.provider.ResourceType;
import ru.goodsreview.api.provider.UrlRequest;

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

    //TODO final
    public UrlRequest requestForListOfCategories(Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName()},
                parameters);
    }

    //TODO final
    public UrlRequest requestForInfoAboutCategoryById(long id) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id)},
                new HashMap<String, String>());
    }

    //TODO final
    public UrlRequest requestForListOfChildrenCategoriesById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.CATEGORY_CHILDREN.getName()},
                parameters);
    }

    //TODO final
    public UrlRequest requestForListOfModelsOfCategoryById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.MODELS.getName()},
                parameters);
    }

    //TODO final
    public UrlRequest requestForListOfFiltersOfCategoryById(long id, Map<String, String> parameters) {
        return build(new String[]{ResourceType.CATEGORY.getName(), String.valueOf(id), Resource.FILTERS.getName()},
                parameters);
    }

    //TODO final
    private UrlRequest build(String [] resources,  Map<String, String> parameters){
        return super.build(resources, parameters, ResourceType.CATEGORY);
    }

}
