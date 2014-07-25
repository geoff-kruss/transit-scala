package transit

import com.cognitect.transit.{ArrayReader, ArrayReadHandler}
import scala.collection.immutable.HashSet
import scala.collection.mutable.ArrayBuffer

object ReadHandlers {

  object SetReadHandler extends ArrayReadHandler {
    def arrayReader() = new ArrayReader {
      def init: AnyRef = new HashSet[AnyRef]

      def init(size: Int): AnyRef = init

      def add(set: AnyRef, item: AnyRef): AnyRef = {
        set.asInstanceOf[HashSet[AnyRef]] + item
      }

      def complete(set: AnyRef): AnyRef = set
    }

    def fromRep(rep: AnyRef) = rep
  }

  object SeqReadHandler extends ArrayReadHandler {
    def arrayReader() = new ArrayReader {
      def init: AnyRef = ArrayBuffer[AnyRef]()

      def init(size: Int): AnyRef = new ArrayBuffer[AnyRef](size)

      def add(buffer: AnyRef, item: AnyRef): AnyRef = {
        buffer.asInstanceOf[ArrayBuffer[AnyRef]] += item
        buffer
      }

      def complete(buffer: AnyRef): AnyRef = buffer
    }

    def fromRep(rep: AnyRef) = rep
  }
}
