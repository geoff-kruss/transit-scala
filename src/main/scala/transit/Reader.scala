package transit

import com.cognitect.transit.{Reader => TReader}
import scala.concurrent.{Future, ExecutionContext, blocking}

class Reader(underlying: TReader)(implicit val exec: ExecutionContext) {

  def read[A](): Future[A] = Future(blocking(underlying.read().asInstanceOf[A]))

}
