package ru.goodsreview.core.util.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import java.sql.SQLException;
import java.util.Properties;

/**
 * User: Alexander Senov
 * User: daddy-bear
 * Date: 22.01.12
 * Time: 21:35
 */
public class SSHTunnelDataSource extends BasicDataSource implements InitializingBean {
    private static final Logger log = Logger.getLogger(SSHTunnelDataSource.class);
    protected JSch jsch;
    protected Session session;
    protected String host;
    protected String sshPassword;
    protected String sshUsername;
    protected int port;
    protected int tunnelLocalPort;
    protected String tunnelRemoteHost;
    protected int tunnelRemotePort;

    @Required
    public void setHost(final String host) {
        this.host = host;
    }

    @Required
    public void setPort(final int port) {
        this.port = port;
    }

    @Required
    public void setTunnelLocalPort(final int tunnelLocalPort) {
        this.tunnelLocalPort = tunnelLocalPort;
    }

    @Required
    public void setTunnelRemoteHost(final String tunnelRemoteHost) {
        this.tunnelRemoteHost = tunnelRemoteHost;
    }

    @Required
    public void setTunnelRemotePort(final int tunnelRemotePort) {
        this.tunnelRemotePort = tunnelRemotePort;
    }

    public void close() {
        session.disconnect();
    }

    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return false;
    }

    public <T> T unwrap(final Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Required
    public void setSshUsername(final String sshUsername) {
        this.sshUsername = sshUsername;
    }

    @Required
    public void setSshPassword(final String sshPassword) {
        this.sshPassword = sshPassword;
    }

    @Override
    public void afterPropertiesSet() {
        jsch = new JSch();
        try {
            session = jsch.getSession(sshUsername, host, port);
            session.setPassword(sshPassword);

            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            log.info("Open ssh connection");
            session.connect();
            session.setPortForwardingL(tunnelLocalPort, tunnelRemoteHost, tunnelRemotePort);
        } catch (Exception e) {
            log.error("Error while connecting", e);
            // It's critical error
            System.exit(1);
        }
    }
}
