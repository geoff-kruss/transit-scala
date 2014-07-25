import com.cognitect.transit.{WriteHandler, ReadHandler, TransitFactory}

import transit.ReadHandlers.SetReadHandler
import transit.WriteHandlers._

import java.io.{InputStream, OutputStream}

import scala.concurrent.ExecutionContext
import scala.collection.JavaConverters._

package object transit {

  sealed trait Format
  case object Msgpack extends Format
  case object Json extends Format
  case object JsonVerbose extends Format

  def writer(out: OutputStream, format: Format)(implicit exec: ExecutionContext): Writer = {
    val customWriteHandlers = Map[Class[_], WriteHandler](classOf[Set[_]] -> SetWriteHandler).asJava
    val underlying = TransitFactory.writer(convertFormat(format), out, customWriteHandlers)
    new Writer(underlying)
  }

  def reader(in: InputStream, format: Format)(implicit exec: ExecutionContext): Reader = {
    val customReadHandlers = Map[String, ReadHandler]("set" -> SetReadHandler).asJava
    val underlying = TransitFactory.reader(convertFormat(format), in, customReadHandlers)
    new Reader(underlying)
  }

  private def convertFormat(format: Format): TransitFactory.Format = format match {
    case Msgpack => TransitFactory.Format.MSGPACK
    case Json => TransitFactory.Format.JSON
    case JsonVerbose => TransitFactory.Format.JSON_VERBOSE
  }

}
