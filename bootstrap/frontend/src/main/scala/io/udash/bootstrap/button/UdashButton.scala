package io.udash.bootstrap
package button

import io.udash._
import io.udash.bootstrap.button.UdashButton.ButtonClickEvent
import org.scalajs.dom
import org.scalajs.dom._

import scalatags.JsDom
import scalatags.JsDom.all._

class UdashButton private(buttonStyle: ButtonStyle, size: ButtonSize, block: Boolean,
                          val active: Property[Boolean], val disabled: Property[Boolean])
                         (mds: Modifier*) extends UdashBootstrapComponent with Listenable[ButtonClickEvent] {

  private lazy val classes: List[Modifier] = buttonStyle :: size ::
    BootstrapStyles.Button.btnBlock.styleIf(block) :: BootstrapStyles.active.styleIf(active) ::
    BootstrapStyles.disabled.styleIf(disabled) :: JsDom.all.disabled.attrIf(disabled) :: Nil

  lazy val render: dom.html.Button =
    button(classes: _*)(onclick :+= ((_: MouseEvent) => {
      fire(ButtonClickEvent(this))
      false
    }))(mds: _*).render

  private[bootstrap] def radio(radioId: String, selected: Property[String]): dom.Element = {
    val inputId: String = UdashBootstrap.newId()
    val in = input(tpe := "radio", name := radioId, id := inputId)
    selected.listen(v => active.set(v == inputId))
    active.listen(v => if (v) selected.set(inputId))
    if (active.get) selected.set(inputId)
    label(classes: _*)(onclick :+= ((_: MouseEvent) => {
      selected.set(inputId)
      fire(ButtonClickEvent(this))
      false
    }))(in)(mds: _*).render
  }
}

object UdashButton {
  case class ButtonClickEvent(button: UdashButton) extends ListenableEvent

  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  def apply(buttonStyle: ButtonStyle = ButtonStyle.Default, size: ButtonSize = ButtonSize.Default, block: Boolean = false,
            active: Property[Boolean] = Property(false), disabled: Property[Boolean] = Property(false))(mds: Modifier*): UdashButton =
    new UdashButton(buttonStyle, size, block, active, disabled)(mds: _*)

  def toggle(buttonStyle: ButtonStyle = ButtonStyle.Default, size: ButtonSize = ButtonSize.Default, block: Boolean = false,
             active: Property[Boolean] = Property(false), disabled: Property[Boolean] = Property(false))(mds: Modifier*): UdashButton = {
    val button = new UdashButton(buttonStyle, size, block, active, disabled)(mds: _*)
    button.listen { case _ => active.set(!active.get) }
    button
  }

}