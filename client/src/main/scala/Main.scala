import scala.scalajs.js
import scala.scalajs.js.annotation._
import org.scalajs.dom
import org.scalajs.dom.html
import sttp.client3._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@JSExportTopLevel("Main")
object Main {

  @JSExport
  def main(): Unit = {
    val bookInput = dom.document.getElementById("book").asInstanceOf[html.Input]
    val chapterInput = dom.document.getElementById("chapter").asInstanceOf[html.Input]
    val resultDiv = dom.document.getElementById("result")

    dom.document.getElementById("fetchButton").addEventListener("click", { (e: dom.Event) =>
      val book = bookInput.value
      val chapter = chapterInput.value
      val url = s"http://localhost:8080/bible/$book/$chapter"

      // Create a basic request using sttp
      val backend = FetchBackend()
      val request = basicRequest.get(uri"$url")

      // Send the request and handle the response
      request.send(backend).map { response =>
        resultDiv.innerHTML = response.body match {
          case Right(body) => body
          case Left(error) => s"Error: $error"
        }
      }.recover { case ex =>
        resultDiv.innerHTML = "Request failed: " + ex.getMessage
      }
    })
  }
}