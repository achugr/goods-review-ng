package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.view.jade.JadeRenderer;
import ru.goodsreview.frontend.view.jade.TemplatePathsHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         10.02.13
 */
@Path("/info")
public class InfoPagesServlet {

    @GET
    @Path("/about")
    @Produces(MediaType.TEXT_HTML)
    public String getAboutPage() {
        return JadeRenderer.render(TemplatePathsHolder.getAboutPageTemplate(), new HashMap<String, Object>());
    }

    @GET
    @Path("/contacts")
    @Produces(MediaType.TEXT_HTML)
    public String getContactsPage() {
        return JadeRenderer.render(TemplatePathsHolder.getContactsPageTemplate(), new HashMap<String, Object>());
    }

}
