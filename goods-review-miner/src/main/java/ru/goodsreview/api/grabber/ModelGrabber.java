package ru.goodsreview.api.grabber;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.goodsreview.api.request.builder.CategoryRequestBuilder;
import ru.goodsreview.api.request.builder.RequestParams;
import ru.goodsreview.api.request.builder.UrlRequest;
import ru.goodsreview.core.db.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public class ModelGrabber extends AbstractGrabber {
    private final static Logger log = Logger.getLogger(ModelGrabber.class);

    private static final Integer COUNT_MAX_VALUE = 30;

    public ModelGrabber() {
        this.entityType = EntityType.MODEL;
    }

    public List<JSONObject> grabModelsToDB(){
        List<JSONObject> allModelsList = new ArrayList<JSONObject>();

        CategoryGrabber categoryGrabber = new CategoryGrabber();
        categoryGrabber.setGrabberBatch(grabberBatch);
        categoryGrabber.setContentApiProvider(contentApiProvider);

        log.info("Grabbing categories started");
        List<JSONObject> categoriesList = categoryGrabber.grabChildCategories();
        log.info("Grabbing categories ended");

        log.info("Grabbing models to DB started");
        try {
            for(JSONObject category : categoriesList){
                long categoryId = category.getLong(JSONKeys.ID.getKey());
                int modelsNum = category.getInt(JSONKeys.MODELS_NUM.getKey());
                if(modelsNum != 0){
                    int pageCount = ( modelsNum / COUNT_MAX_VALUE ) + 1;
                    for(Integer pageNum = 1; pageNum <= pageCount; pageNum++){
                        CategoryRequestBuilder categoryRequestBuilder = new CategoryRequestBuilder();

                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put(RequestParams.COUNT.getKey(), COUNT_MAX_VALUE.toString());
                        parameters.put(RequestParams.PAGE.getKey(), pageNum.toString());

                        UrlRequest urlRequest = categoryRequestBuilder.requestForListOfModelsOfCategoryById(categoryId, parameters);

                        List<JSONObject> modelsList = contentApiProvider.provideAsList(urlRequest, JSONKeys.ITEMS.getKey(), JSONKeys.MODELS.getKey());
                        processEntityList(modelsList);
                        allModelsList.addAll(modelsList);
                    }
                }
            }
            log.info("Grabbing models to DB ended");
        } catch (JSONException e) {
            log.error("some problems with json", e);
            throw new RuntimeException();
        }
        return allModelsList;
    }
}
