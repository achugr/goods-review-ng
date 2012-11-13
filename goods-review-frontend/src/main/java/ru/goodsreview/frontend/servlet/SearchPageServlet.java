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

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getSearchResults(@QueryParam("q") final String searchQuery){
        return new SearchPageController().generatePage(searchQuery);
    }
}
