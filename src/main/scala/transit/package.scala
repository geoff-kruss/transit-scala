import com.cognitect.transit.TransitFactory
import java.io.{InputStream, OutputStream}
import scala.concurrent.ExecutionContext

package object transit {

  sealed trait Format
  case object Msgpack extends Format
  case object Json extends Format
  case object JsonVerbose extends Format

  def writer(out: OutputStream, format: Format)(implicit exec: ExecutionContext): Writer = {
    val underlying = TransitFactory.writer(convertFormat(format), out)
    new Writer(underlying)
  }

  def reader(in: InputStream, format: Format)(implicit exec: ExecutionContext): Reader = {
    val underlying = TransitFactory.reader(convertFormat(format), in)
    new Reader(underlying)
  }

  private def convertFormat(format: Format): TransitFactory.Format = format match {
    case Msgpack => TransitFactory.Format.MSGPACK
    case Json => TransitFactory.Format.JSON
    case JsonVerbose => TransitFactory.Format.JSON_VERBOSE
  }

}
