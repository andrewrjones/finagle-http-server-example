package com.andrewjones

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{ Http, RichHttp, Request, Response, Version, Status }
import com.twitter.finagle.Service
import com.twitter.util.Future
import java.net.InetSocketAddress

object Server {

  val service = new Service[Request, Response] {
    def apply(request: Request): Future[Response] = {
      val response = Response()
      response.setContentString("Hello from Finagle\n")
      Future.value(response)
    }
  }

  val address = new InetSocketAddress(10000)
  
  def start() = ServerBuilder()
      .codec(new RichHttp[Request](Http()))
      .name("HttpServer")
      .bindTo(address)
      .build(service)

  def main(args: Array[String]){
    println("Start HTTP server on port 10000")
    val server = start()
  }
}
