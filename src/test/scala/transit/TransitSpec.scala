package transit

import org.scalatest.{Matchers, FlatSpec}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import org.scalatest.concurrent.ScalaFutures

/**
 * Author: chris
 * Created: 7/25/14
 */
class TransitSpec extends FlatSpec with Matchers with ScalaFutures {

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

  it should "round-trip a Scala Set" in {
    checkRoundTrip(Set(1, 2, 3))
  }

  it should "round-trip a Scala Seq" in {
    pending // not implemented yet
    checkRoundTrip(Seq(1, 2, 3))
  }

  it should "round-trip a Scala Map" in {
    pending // not implemented yet
    checkRoundTrip(Map(1 -> "a", 2 -> "b", 3 -> "c"))
  }

  case class Foo(a: Int, b: String)

  it should "round-trip a Scala case class" in {
    pending // not implemented yet
    checkRoundTrip(Foo(123, "abc"))
  }

  def checkRoundTrip[A](value: A) = {
    for (format <- Seq(Msgpack, Json, JsonVerbose)) {
      val baos = new ByteArrayOutputStream

      whenReady(transit.writer(baos, format).write(value)) { _ =>
        val bais = new ByteArrayInputStream(baos.toByteArray)

        whenReady(transit.reader(bais, format).read[A]()) { result =>
          result should be(value)
        }
      }
    }
  }

}
