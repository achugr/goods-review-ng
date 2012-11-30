package ru.goodsreview.frontend.servlet.api;


import org.json.JSONObject;
import ru.goodsreview.frontend.controller.api.ModelApiController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         30.11.12
 */
@Path("/api/model")
public class ModelApiHandle {
    private final ModelApiController modelApiController = new ModelApiController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{model-id}")
    public String getModel(@PathParam("model-id") final long modelId){
        return modelApiController.getModelById(modelId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{model-id}/reviews")
    public String getReviews(@PathParam("model-id") final long modelId){
        return modelApiController.getReviewsOnModel(modelId);
    }

}
