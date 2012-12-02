package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.controller.SearchPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author: Mokaev Timur
 * Date: 11.11.12
 * Time: 11:51
 */
@Path("/search")
public class SearchPageServlet {
    private final static int DEFAULT_MODELS_ON_PAGE = 9;


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getSearchResults(@QueryParam("query") final String searchQuery, @QueryParam("page-number") int pageNumber, @QueryParam("models-on-page") int modelsOnPage){
        if(pageNumber < 1){
            pageNumber = 1;
        }
        if(modelsOnPage < 1){
            modelsOnPage = DEFAULT_MODELS_ON_PAGE;
        }
        return new SearchPageController().generatePage(searchQuery, pageNumber, modelsOnPage);
    }
}
