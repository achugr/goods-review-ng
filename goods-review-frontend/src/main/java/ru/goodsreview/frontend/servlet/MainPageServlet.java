package ru.goodsreview.frontend.servlet;

import ru.goodsreview.frontend.controller.MainPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
@Path("/")
public class MainPageServlet {
    private final MainPageController controller = new MainPageController();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getMainPage() {
        return controller.generatePage();
    }
}
