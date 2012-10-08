package ru.goodsreview.frontend.servlet;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import ru.goodsreview.core.util.FileReader;
import ru.goodsreview.frontend.model.MainPageModel;
import ru.goodsreview.frontend.view.MainPageView;

import javax.servlet.Servlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         06.10.12
 */
@Path("/")
public class MainPageServlet {

    private MainPageModel mainPageModel;

    @Required
    public void setMainPageModel(MainPageModel mainPageModel) {
        this.mainPageModel = mainPageModel;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getMainPage() {
        System.out.println(mainPageModel);
        final List<JSONObject> data = mainPageModel.getPopularProducts(6);
        return new MainPageView().createPage(data);
    }
}
