package io.udash.rest

import io.udash.rest.internal.{RESTConnector, UsesREST}
import io.udash.rpc.serialization.URLEncoder

import scala.concurrent.ExecutionContext

/** Default REST usage mechanism using [[io.udash.rest.DefaultRESTFramework]]. */
class DefaultServerREST[ServerRPCType : DefaultRESTFramework.AsRealRPC : DefaultRESTFramework.RPCMetadata : DefaultRESTFramework.ValidREST]
                       (override protected val connector: RESTConnector)(implicit ec: ExecutionContext)
  extends UsesREST[ServerRPCType] {

  override val framework = DefaultRESTFramework

  override val remoteRpcAsReal: DefaultRESTFramework.AsRealRPC[ServerRPCType] = implicitly[DefaultRESTFramework.AsRealRPC[ServerRPCType]]
  override val rpcMetadata: framework.RPCMetadata[ServerRPCType] = implicitly[framework.RPCMetadata[ServerRPCType]]

  def rawToHeaderArgument(raw: framework.RawValue): String =
    stripQuotes(framework.rawToString(raw))
  def rawToQueryArgument(raw: framework.RawValue): String =
    stripQuotes(framework.rawToString(raw))
  def rawToURLPart(raw: framework.RawValue): String =
    URLEncoder.encode(stripQuotes(framework.rawToString(raw)))

  private def stripQuotes(s: String): String =
    s.stripPrefix("\"").stripSuffix("\"")
}

object DefaultServerREST {
  /** Creates [[io.udash.rest.DefaultServerREST]] with [[io.udash.rest.DefaultRESTConnector]] for provided REST interfaces. */
  def apply[ServerRPCType : DefaultRESTFramework.AsRealRPC : DefaultRESTFramework.RPCMetadata : DefaultRESTFramework.ValidREST]
           (host: String, port: Int, pathPrefix: String = "")(implicit ec: ExecutionContext): ServerRPCType = {
    val serverConnector = new DefaultRESTConnector(host, port, pathPrefix)
    val serverRPC: DefaultServerREST[ServerRPCType] = new DefaultServerREST[ServerRPCType](serverConnector)
    serverRPC.remoteRpc
  }
}