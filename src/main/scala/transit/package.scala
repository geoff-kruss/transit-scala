import com.cognitect.transit.{TransitFactory, Writer, Reader}
import java.io.{InputStream, OutputStream}

package object transit {

  sealed trait Format
  case object Msgpack extends Format
  case object Json extends Format
  case object JsonVerbose extends Format

  def writer(out: OutputStream, format: Format): Writer = TransitFactory.writer(TransitFactory.Format.JSON_VERBOSE, out)

  def reader(in: InputStream, format: Format): Reader = TransitFactory.reader(TransitFactory.Format.JSON_VERBOSE, in)

}
