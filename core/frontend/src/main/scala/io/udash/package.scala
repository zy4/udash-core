package io

package object udash extends io.udash.bindings.Bindings
                        with io.udash.properties.Properties
                        with io.udash.routing.Routing
{
  // Defaults
  @deprecated("Renamed to `StaticViewFactory`.", "0.6.0")
  type DefaultViewPresenterFactory[S <: State] = io.udash.core.StaticViewFactory[S]
  type StaticViewFactory[S <: State] = io.udash.core.StaticViewFactory[S]
  type EmptyPresenter[S <: State] = io.udash.core.EmptyPresenter[S]
  val  Window = io.udash.core.Window

  // Definitions
  val  Url = io.udash.core.Url
  type Url = io.udash.core.Url

  type Presenter[S <: State] = io.udash.core.Presenter[S]

  @deprecated("Use `ContainerViewFactory`, `FinalViewFactory` or `ViewFactory` instead.", "0.6.0")
  type ViewPresenter[S <: State] = io.udash.core.ViewFactory[S]
  type ViewFactory[S <: State] = io.udash.core.ViewFactory[S]
  type ContainerViewFactory[S <: ContainerState] = io.udash.core.ContainerViewFactory[S]
  type FinalViewFactory[S <: FinalState] = io.udash.core.FinalViewFactory[S]

  type View = io.udash.core.View
  type ContainerView = io.udash.core.ContainerView
  type FinalView = io.udash.core.FinalView

  type State = io.udash.core.State
  type ContainerState = io.udash.core.ContainerState
  type FinalState = io.udash.core.FinalState

  type RoutingRegistry[S <: State] = io.udash.core.RoutingRegistry[S]

  @deprecated("Renamed to `ViewFactoryRegistry`.", "0.6.0")
  type ViewPresenterRegistry[S <: State] = io.udash.core.ViewFactoryRegistry[S]
  type ViewFactoryRegistry[S <: State] = io.udash.core.ViewFactoryRegistry[S]

  // Utils
  type Registration = io.udash.utils.Registration
  type StrictLogging = io.udash.utils.StrictLogging
  type FileUploader = io.udash.utils.FileUploader
  val  FileUploader = io.udash.utils.FileUploader

  // View
  type ViewRenderer = io.udash.view.ViewRenderer
}
