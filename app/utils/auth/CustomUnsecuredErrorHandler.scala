package utils.auth

import play.api.libs.json.Json
import play.api.mvc.RequestHeader
import play.api.mvc.Results._
import play.silhouette.api.actions.UnsecuredErrorHandler

import scala.concurrent.Future

/**
 * Custom unsecured error handler.
 */
class CustomUnsecuredErrorHandler extends UnsecuredErrorHandler {

  /**
   * Called when a user is authenticated but not authorized.
   *
   * As defined by RFC 2616, the status code of the response should be 403 Forbidden.
   *
   * @param request The request header.
   * @return The result to send to the client.
   */
  override def onNotAuthorized(implicit request: RequestHeader) = {
    val jsonResponse = Json.obj(
      "code" -> 403,
      "message" -> "User is authenticated but not authorized."
    )
    Future.successful(Forbidden(jsonResponse))
  }
}
