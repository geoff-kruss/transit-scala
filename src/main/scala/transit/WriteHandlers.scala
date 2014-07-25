package transit

import com.cognitect.transit.impl.AbstractWriteHandler
import com.cognitect.transit.TransitFactory
import collection.JavaConverters._

object WriteHandlers {

//  object SeqWriteHandler extends AbstractWriteHandler {
//    def tag(a: AnyRef): String = a match {
//      case _: IndexedSeq[_] => return "array"
//      case _: Seq[_] => return "list"
//      case _ => throw new UnsupportedOperationException("Cannot marshal type as list: " + a.getClass.getSimpleName)
//    }
//
//    def rep(a: AnyRef): AnyRef = a match {
//      case _: IndexedSeq[_] => TransitFactory.taggedValue("array", a)
//      case _: Seq[_] => a
//      case _ => throw new UnsupportedOperationException("Cannot marshal type as list: " + a.getClass.getSimpleName)
//    }
//  }

  object SetWriteHandler extends AbstractWriteHandler {

    def tag(a: AnyRef) = "set"

    def rep(a: AnyRef) = TransitFactory.taggedValue("array", a.asInstanceOf[Set[_]].asJava)

  }

}
