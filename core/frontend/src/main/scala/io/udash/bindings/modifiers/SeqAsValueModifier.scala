package io.udash.bindings.modifiers

import io.udash.properties.seq.ReadableSeqProperty
import io.udash.properties.single.ReadableProperty
import io.udash.utils.Registration
import org.scalajs.dom

private[bindings]
class SeqAsValueModifier[T](override val property: ReadableSeqProperty[T, _ <: ReadableProperty[T]],
                            override val builder: (Seq[T], Binding => Binding) => Seq[dom.Element])
  extends ValueModifier[Seq[T]] {

  def this(property: ReadableSeqProperty[T, _ <: ReadableProperty[T]], builder: Seq[T] => Seq[dom.Element]) = {
    this(property, (data: Seq[T], _: Binding => Binding) => builder(data))
  }

  override def listen(callback: Seq[T] => Unit): Registration =
    property.listen(callback)

  override def checkNull: Boolean = false // SeqProperty can not return null from `get` method

}








