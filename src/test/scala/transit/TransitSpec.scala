package transit

import org.scalatest.{Matchers, FlatSpec}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

/**
 * Author: chris
 * Created: 7/25/14
 */
class TransitSpec extends FlatSpec with Matchers {

  behavior of "transit-scala"

  it should "round-trip a Scala Int" in {
    val baos = new ByteArrayOutputStream
    transit.writer(baos, JsonVerbose).write(123)

    val bais = new ByteArrayInputStream(baos.toByteArray)
    transit.reader(bais, JsonVerbose).read().asInstanceOf[Int] should be(123)
  }

}
