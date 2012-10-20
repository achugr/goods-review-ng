package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.controller.ProductController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         11.10.12
 */
@Path("/product")
public class ProductPageServlet {

    @GET
    @Path("/{modelId}")
    @Produces(MediaType.TEXT_HTML)
    public String getProductPageById(@PathParam("modelId") final long modelId){
        return new ProductController().generatePage(modelId);
    }

}
