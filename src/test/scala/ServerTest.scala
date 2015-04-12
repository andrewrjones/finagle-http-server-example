package com.andrewjones

import org.jboss.netty.handler.codec.http.{ HttpRequest, HttpResponse, DefaultHttpRequest }
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{ Http, Response, Request, Version, Method, Status }
import com.twitter.finagle.Service
import com.twitter.util.{ Await, Future, Closable }
import java.net.InetSocketAddress
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfterEach

@RunWith(classOf[JUnitRunner])
class ServerTest extends FunSuite with BeforeAndAfterEach {
  var server: com.twitter.finagle.builder.Server = _
  var client: Service[HttpRequest, HttpResponse] = _
  override def beforeEach() {
    server = Server.start()
    client = ClientBuilder()
      .codec(Http())
      .hosts(Seq(server.localAddress))
      .hostConnectionLimit(1)
      .build()
  }
  override def afterEach() {
    Closable.all(server, client).close()
  }

  test("GET Ok") {
    val request = new DefaultHttpRequest(Version.Http11, Method.Get, "/")
    val responseFuture = client(request)
    val response = Response(Await.result(responseFuture))

    assert(response.getStatus() === Status.Ok)
  }
}