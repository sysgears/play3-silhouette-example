package controllers

import models.User
import org.apache.pekko.http.scaladsl.model.DateTime
import play.api.i18n.Lang
import play.api.libs.json._
import play.api.mvc.{ AnyContent, Request }
import play.silhouette.api.LoginInfo
import play.silhouette.impl.providers.CredentialsProvider

import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }

/**
 * The `Sign Up` controller.
 */
class SignUpController @Inject() (components: SilhouetteControllerComponents)(implicit ex: ExecutionContext) extends SilhouetteController(components) {

  /**
   * Handles sign up request
   *
   * @return The result to display.
   */
  def signUp = UnsecuredAction.async { implicit request: Request[AnyContent] =>
    implicit val lang: Lang = supportedLangs.availables.head
    request.body.asJson.flatMap(_.asOpt[User]) match {
      case Some(newUser) if newUser.password.isDefined =>
        userService.retrieve(LoginInfo(CredentialsProvider.ID, newUser.email)).flatMap {
          case Some(_) =>
            Future.successful(Conflict(JsString(messagesApi("user.already.exist"))))
          case None =>
            val authInfo = passwordHasherRegistry.current.hash(newUser.password.get)
            val user = newUser.copy(password = Some(authInfo.password), dateOfCreation = Option(DateTime.now))
            userService.save(user).map(u => Ok(Json.toJson(u.copy(password = None))))
        }
      case _ => Future.successful(BadRequest(JsString(messagesApi("invalid.body"))))
    }
  }
}
