package ru.goodsreview.frontend.core;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Holder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         07.10.12
 */
public class HttpServerInitializer implements InitializingBean {

    private int port;

    @Required
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Server server = new Server();
// TODO configure webAppContexts by spring
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.addConnector(connector);

        WebAppContext root = new WebAppContext("goods-review-frontend/src/main/webapp/", "/");
        server.setHandlers(new Handler[]{root});

        server.start();
    }
}
