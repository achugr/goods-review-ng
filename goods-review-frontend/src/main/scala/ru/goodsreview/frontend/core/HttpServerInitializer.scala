package ru.goodsreview.frontend.core

import org.springframework.beans.factory.InitializingBean
import org.mortbay.jetty.{Connector, Server, Handler}
import reflect.BeanProperty
import org.mortbay.thread.QueuedThreadPool
import org.mortbay.jetty.handler.HandlerCollection
import org.mortbay.jetty.nio.SelectChannelConnector
import org.apache.log4j.Logger

/**
 * @author Dmitry Batkovich
 */
class HttpServerInitializer extends InitializingBean {

  @BeanProperty
  var port: Int = _

  @BeanProperty
  var maxThreads: Int = _

  @BeanProperty
  var handlers: Array[Handler] = _

  private val server: Server = new Server()

  override def afterPropertiesSet() {
    val st = System.currentTimeMillis
    try {
      val connector = new SelectChannelConnector
      connector.setPort(port)

      val handlerCollection = new HandlerCollection()
      handlerCollection.setHandlers(handlers)

      server.setConnectors(Array(connector))
      server.setThreadPool(new QueuedThreadPool(maxThreads))
      server.setHandler(handlerCollection)
      server.start()

      log.info("Server started: " + (System.currentTimeMillis - st) + " ms")
    } catch {
      case e: Exception => log.error("Could not initialize server: ", e)
    }
  }

  def log = Helper.log
}

private object Helper {
  val log = Logger getLogger classOf[HttpServerInitializer]
}
