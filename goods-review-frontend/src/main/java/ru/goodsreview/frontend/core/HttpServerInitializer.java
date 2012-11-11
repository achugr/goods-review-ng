package ru.goodsreview.frontend.core;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Holder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         07.10.12
 */
public class HttpServerInitializer implements InitializingBean, DisposableBean {

    private int port;

    private Handler[] handlers;

    @Required
    public void setPort(final int port) {
        this.port = port;
    }

    @Required
    public void setHandlers(final Handler[] handlers) {
        this.handlers = handlers;
    }

    private Server server;

    @Override
    public void afterPropertiesSet() throws Exception {
        server = new Server();
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.addConnector(connector);
        server.setHandlers(handlers);
        server.start();
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
    }
}
