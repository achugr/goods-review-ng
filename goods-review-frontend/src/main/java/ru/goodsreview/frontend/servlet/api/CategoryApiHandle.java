package ru.goodsreview.frontend.servlet.api;

import org.apache.commons.jexl2.internal.AbstractExecutor;
import ru.goodsreview.frontend.controller.api.CategoryApiController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         25.11.12
 */
@Path("/api/category")
public class CategoryApiHandle {
    private final static int DEFAULT_MODELS_ON_PAGE = 9;

    private final CategoryApiController controller = new CategoryApiController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getData(@QueryParam("category-id") long categoryId, @QueryParam("page") int pageNumber, @QueryParam("models-on-page") int modelsOnPage) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (modelsOnPage < 1) {
            modelsOnPage = DEFAULT_MODELS_ON_PAGE;
        }
        return controller.getData(categoryId, pageNumber, modelsOnPage);
    }
}
