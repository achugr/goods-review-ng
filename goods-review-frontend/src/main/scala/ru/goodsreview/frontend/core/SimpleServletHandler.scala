package ru.goodsreview.frontend.core

import org.mortbay.jetty.servlet.{ServletHolder, ServletHandler}
import javax.servlet.Servlet

/**
 * @author Dmitry Batkovich <daddy-bear@yandex-team.ru>
 */
class SimpleServletHandler extends ServletHandler {

  def setServlets1(servlets: Array[Servlet]) {
    setServlets(servlets.map {new ServletHolder(_)})
    print(servlets.map{new ServletHolder(_)})
  }

}
