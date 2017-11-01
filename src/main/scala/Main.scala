import java.net.{HttpURLConnection, URL}
import java.util.Base64

object Main {

  //private class auth(var user: String, var pass: String, var sendImmediately: Boolean) {}

  final val API_URL = "https://myanimelist.net/api/anime/search.xml?q="
  final val API_USER = ""
  final val API_PASSWORD = ""

  def get(url: String, timeout : Int = 5000): String = {
    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    val auth = API_USER + ":" + API_PASSWORD
    connection.setConnectTimeout(timeout)
    connection.setRequestMethod("GET")
    connection.setRequestProperty("Authorization", "Basic " + new String(Base64.getEncoder.encode(auth.getBytes())))
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close()
    content
  }

  def handleInput() {
    var arg = scala.io.StdIn.readLine()
    arg = arg.replaceAll(" ", "+")
    try {
      println(get(API_URL + arg))
    } catch {
      case ioe: java.io.IOException => ioe.printStackTrace()
      case ste: java.net.SocketTimeoutException => ste.printStackTrace()
    }
  }

  def main(args: Array[String]): Unit = {
    while (true) {
      handleInput()
    }
  }
}