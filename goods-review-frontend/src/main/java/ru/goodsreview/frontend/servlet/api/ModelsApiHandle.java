package ru.goodsreview.frontend.servlet.api;

import ru.goodsreview.frontend.controller.api.ModelsApiController;
import ru.goodsreview.frontend.controller.api.SearchApiController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         25.11.12
 */
@Path("/api/models")
public class ModelsApiHandle {
    private final static int DEFAULT_MODELS_ON_PAGE = 9;
    private final ModelsApiController modelsApiServlet = new ModelsApiController();
    private final SearchApiController searchApiController = new SearchApiController();

    @GET
    @Path("/popular")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPopular(@QueryParam("page-number") final int pageNumber, @QueryParam("models-on-page") final int modelsOnPage) {

        return modelsApiServlet.getPopularProducts(modelsOnPage);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public String doSearch(@QueryParam("query") final String query, @QueryParam("page-number") int pageNumber, @QueryParam("models-on-page") int modelsOnPage) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (modelsOnPage < 1) {
            modelsOnPage = DEFAULT_MODELS_ON_PAGE;
        }
        return searchApiController.getModelsByQuery(query, pageNumber, modelsOnPage);
    }
}
