package transit

import com.cognitect.transit.{ArrayReader, ArrayReadHandler}
import scala.collection.immutable.HashSet

object ReadHandlers {

  object SetReadHandler extends ArrayReadHandler {
    def arrayReader() = new ArrayReader {
      def init: AnyRef = new HashSet[AnyRef]

      def init(size: Int): AnyRef = init

      def add(set: AnyRef, item: AnyRef): AnyRef = {
        set.asInstanceOf[HashSet[AnyRef]] + item
      }

      def complete(ar: AnyRef): AnyRef = ar
    }

    def fromRep(rep: AnyRef) = rep
  }

}
