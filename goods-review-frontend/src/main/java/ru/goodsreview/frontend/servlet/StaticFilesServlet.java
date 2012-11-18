package ru.goodsreview.frontend.servlet;

import ru.goodsreview.core.util.FileUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         07.10.12
 */
@Deprecated
@Path("/static")
public class StaticFilesServlet {
    private static final String CSS_DIR = "goods-review-frontend/src/main/css/";
    private static final String JS_DIR = "goods-review-frontend/src/main/js/";

    @GET
    @Path("/css/{fileName}")
    @Produces("text/css")
    public String getCss(@PathParam("fileName") String fileName){
        return FileUtils.readFileAsString(CSS_DIR + fileName);
    }

    @GET
    @Path("/js/{fileName}")
    @Produces("text/javascript")
    public String getJs(@PathParam("fileName") String fileName){
        return FileUtils.readFileAsString(JS_DIR + fileName);
    }
}
