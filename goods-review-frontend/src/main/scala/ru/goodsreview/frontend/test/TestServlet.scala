package ru.goodsreview.frontend.test

import org.scalatra.ScalatraServlet

/**
 * @author Dmitry Batkovich
 */
class TestServlet extends ScalatraServlet {

  get("/hello/:name") {
    // Matches "GET /hello/foo" and "GET /hello/bar"
    // params("name") is "foo" or "bar"
    <p>Hello, {params("name")}</p>
  }

}
