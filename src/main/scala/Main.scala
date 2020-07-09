import org.scalajs.dom
import org.scalajs.dom.experimental.serviceworkers.{FetchEvent}
import org.scalajs.dom.experimental.{Request, Response, ResponseInit}
import scala.scalajs.js
import js.JSConverters._
import scala.concurrent.ExecutionContext.Implicits.global

object Main {
  def main(args: Array[String]): Unit = {
    Globals.addEventListener("fetch", (event: FetchEvent) => {
      event.respondWith(handleRequest(event.request).toJSPromise)
    })
  }

  def handleRequest(request: Request) = {
    val path = request.url.split("/")
    val k = if (path.size < 4) "foo" else path(3)
    Globals.KV.get(k).toFuture.map {
      (v: String) => {
        new Response(
          s"hello from scalajs, your value for '$k' is '$v'",
          ResponseInit(
            _headers = js.Dictionary("content-type" -> "text/plain")
          )
        )
      }
    } recover {
      case err => {
        new Response(
          s"error getting a value for '$k': $err",
          ResponseInit(
            _status = 400,
            _headers = js.Dictionary("content-type" -> "text/plain")
          )
        )
      }
    }
  }
}
