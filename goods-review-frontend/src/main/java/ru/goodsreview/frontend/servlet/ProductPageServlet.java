package ru.goodsreview.frontend.servlet;

import org.json.JSONObject;
import ru.goodsreview.core.util.Pair;
import ru.goodsreview.frontend.controller.ProductPageController;
import ru.goodsreview.frontend.model.ProductPageModel;
import ru.goodsreview.frontend.view.ProductPageView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return new ProductPageController().generatePage(modelId);
    }

}