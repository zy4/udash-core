package io.udash.properties
package single

import java.util.UUID

import io.udash.utils.Registration

import scala.concurrent.{ExecutionContext, Future}

/** Represents ReadableProperty[A] transformed to ReadableProperty[B]. */
private[properties]
class TransformedReadableProperty[A, B](override protected val origin: ReadableProperty[A],
                                        transformer: A => B) extends ForwarderReadableProperty[B] {
  override def listen(valueListener: (B) => Any): Registration =
    origin.listen((a: A) => valueListener(transformer(a)))

  override def listenOnce(valueListener: (B) => Any): Registration =
    origin.listenOnce((a: A) => valueListener(transformer(a)))

  override protected[properties] def fireValueListeners(): Unit =
    origin.fireValueListeners()

  override def get: B =
    transformer(origin.get)
}

/** Represents Property[A] transformed to Property[B]. */
private[properties]
class TransformedProperty[A, B](override protected val origin: Property[A], transformer: A => B, revert: B => A)
  extends TransformedReadableProperty[A, B](origin, transformer) with ForwarderProperty[B] {

  override def set(t: B, force: Boolean = false): Unit =
    origin.set(revert(t), force)

  override def setInitValue(t: B): Unit =
    origin.setInitValue(revert(t))

  override def touch(): Unit =
    origin.touch()

  override def addValidator(v: Validator[B]): Registration =
    origin.addValidator(new Validator[A] {
      override def apply(element: A)(implicit ec: ExecutionContext): Future[ValidationResult] =
        v(transformer(element))(ec)
    })

  override def clearValidators(): Unit =
    origin.clearValidators()

  override def clearListeners(): Unit =
    origin.clearListeners()
}
