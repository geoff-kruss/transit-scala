package transit

import org.scalatest.{Matchers, FlatSpec}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import org.scalatest.concurrent.ScalaFutures
import java.util

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
    checkRoundTrip(Seq(1, 2, 3))
  }

  it should "unmarshall an array tagged with 'list' into a Seq" in {
    val jList = new util.LinkedList[java.lang.Integer]()
    jList.add(1)
    jList.add(2)
    jList.add(3)
    checkRoundTrip(jList, Seq(1, 2, 3))
  }

  it should "round trip an Array" in {
    pending
    checkRoundTrip(Array(1, 2, 3))
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

  def checkRoundTrip[A](value: A): Unit = checkRoundTrip(value, value)

  def checkRoundTrip[A, B](from: A, to: B): Unit = {
    for (format <- Seq(Msgpack, Json, JsonVerbose)) {
      val baos = new ByteArrayOutputStream

      whenReady(transit.writer(baos, format).write(from)) { _ =>
        val bais = new ByteArrayInputStream(baos.toByteArray)

        whenReady(transit.reader(bais, format).read[B]()) { result =>
          result should be(to)
        }
      }
    }
  }

}
