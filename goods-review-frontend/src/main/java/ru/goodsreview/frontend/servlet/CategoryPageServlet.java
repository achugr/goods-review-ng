package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.controller.CategoryController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: timur
 * Date: 18.10.12
 * Time: 15:36
 */

@Path("/category")
public class CategoryPageServlet {
    private final CategoryController controller = new CategoryController();

    @GET
    @Path("/{category_id}/page/{page_number}")
    @Produces(MediaType.TEXT_HTML)
    public String getProductPageById(@PathParam("category_id") final long categoryId, @PathParam("page_number") final int pageNumber){
        return controller.generatePage(categoryId, pageNumber);
    }
}
