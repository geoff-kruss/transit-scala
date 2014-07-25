package transit

import com.cognitect.transit.{Writer => TWriter}
import scala.concurrent.{Future, ExecutionContext, blocking}

class Writer(underlying: TWriter)(implicit val exec: ExecutionContext) {

  def write(a: Any): Future[Unit] = Future(blocking(underlying.write(a)))

}
