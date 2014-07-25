package transit

import com.cognitect.transit.impl.AbstractWriteHandler
import com.cognitect.transit.TransitFactory
import collection.JavaConverters._

object WriteHandlers {

  object SeqWriteHandler extends AbstractWriteHandler {
    def tag(a: AnyRef): String = a match {
      case _: Seq[_] => "list"
      case _ => throw new UnsupportedOperationException("Cannot marshal type as seq: " + a.getClass.getSimpleName)
    }

    def rep(a: AnyRef): AnyRef = a match {
      case _: Seq[_] => TransitFactory.taggedValue("array", a.asInstanceOf[Seq[_]].asJava)
      case _ => throw new UnsupportedOperationException("Cannot marshal type as seq: " + a.getClass.getSimpleName)
    }
  }

  object SetWriteHandler extends AbstractWriteHandler {

    def tag(a: AnyRef) = "set"

    def rep(a: AnyRef) = TransitFactory.taggedValue("array", a.asInstanceOf[Set[_]].asJava)

  }

}
