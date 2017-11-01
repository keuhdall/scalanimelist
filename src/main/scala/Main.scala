import java.net.{URL, HttpURLConnection}
import com.google.gson.Gson
object Main {

  private class auth(var user: String, var pass: String, var sendImmediately: Boolean) {}

  final val API_URL = "https://myanimelist.net/api/anime/search.xml?q="
  final val API_USER = ""
  final val API_PASSWORD = ""

  def get(url: String, timeout : Int = 5000): String = {
    val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
    val auth = new auth(API_USER, API_PASSWORD, false)
    connection.setConnectTimeout(timeout)
    connection.setRequestMethod("GET")
    connection.setRequestProperty("auth", new Gson().toJson(auth))
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close()
    content
  }

  def main(args: Array[String]): Unit = {
    var arg = scala.io.StdIn.readLine()
    arg = arg.replaceAll(" ", "+")
    try {
      print(get(API_URL + arg))
    } catch {
      case ioe: java.io.IOException => ioe.printStackTrace()
      case ste: java.net.SocketTimeoutException => ste.printStackTrace()
    }
  }
}