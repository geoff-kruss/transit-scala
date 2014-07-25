package transit

import org.scalatest.{Matchers, FlatSpec}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

/**
 * Author: chris
 * Created: 7/25/14
 */
class TransitSpec extends FlatSpec with Matchers {

  behavior of "transit-scala"

  import scala.concurrent.ExecutionContext.Implicits.global

  it should "round-trip a Scala Boolean" in {
    checkRoundTrip(true)
  }

  it should "round-trip a Scala Long" in {
    checkRoundTrip[Long](123L)
  }

  it should "round-trip a Java Long" in {
    checkRoundTrip[java.lang.Long](123: java.lang.Long)
  }

  it should "round-trip a Scala Int" in {
    checkRoundTrip[Int](123)
  }

  def checkRoundTrip[A](value: A) = {
    for (format <- Seq(Msgpack, Json, JsonVerbose)) {
      val baos = new ByteArrayOutputStream
      for {
        _ <- transit.writer(baos, format).write(value)
        bais = new ByteArrayInputStream(baos.toByteArray)
        result <- transit.reader(bais, format).read[A]()
      } {
        result should be(value)
      }
    }
  }

}
