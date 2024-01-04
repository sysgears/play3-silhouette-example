package utils.auth

import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.libs.json.Json
import play.api.mvc.RequestHeader
import play.api.mvc.Results._
import play.silhouette.api.actions.SecuredErrorHandler

import javax.inject.Inject
import scala.concurrent.Future

/**
 * Custom secured error handler.
 *
 * @param messagesApi The Play messages API.
 */
class CustomSecuredErrorHandler @Inject() (val messagesApi: MessagesApi) extends SecuredErrorHandler with I18nSupport {
  /**
   * Called when a user is not authenticated.
   *
   * As defined by RFC 2616, the status code of the response should be 401 Unauthorized.
   *
   * @param request The request header.
   * @return The result to send to the client.
   */
  override def onNotAuthenticated(implicit request: RequestHeader) = {
    val jsonResponse = Json.obj(
      "code" -> 401,
      "message" -> "Authentication failed. Given policy has not granted."
    )
    Future.successful(Unauthorized(jsonResponse))
  }

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
