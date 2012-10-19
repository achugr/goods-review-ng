package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.controller.CategoryPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by IntelliJ IDEA.
 * User: timur
 * Date: 18.10.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */

@Path("/category")
public class CategoryPageServlet {
    @GET
    @Path("/{category_id}/page/{page_number}")
    @Produces(MediaType.TEXT_HTML)
    public String getProductPageById(@PathParam("category_id") final long categoryId, @PathParam("page_number") final int pageNumber){
        return new CategoryPageController().generatePage(categoryId, pageNumber);
    }
}
