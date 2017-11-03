import java.net.{HttpURLConnection, URL}
import java.util.Base64
import scala.xml.XML

object Main {
  final val API_URL = "https://myanimelist.net/api/anime/search.xml?q="
  var API_USER:String = null
  var API_PASSWORD:String = null

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

  val checkCredentials: () => Unit = () => {
    if (API_USER == null || API_PASSWORD == null) {
      API_USER = scala.io.StdIn.readLine("Enter your myanimelist username : ")
      API_PASSWORD = scala.io.StdIn.readLine("Enter your myanimelist password : ")
    }
  }

  def handleInput() {
    checkCredentials()
    var arg = scala.io.StdIn.readLine("Search your anime : ")
    arg = arg.replaceAll(" ", "+")
    try {
      val xmlElem = XML.loadString(get(API_URL + arg))
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