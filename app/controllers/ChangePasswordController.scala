package controllers

import play.api.i18n.Lang
import play.api.libs.json.{ JsString, Json, OFormat }
import play.api.mvc._
import play.silhouette.api.actions.SecuredRequest
import play.silhouette.api.exceptions.ProviderException
import play.silhouette.api.util.Credentials
import play.silhouette.impl.providers.CredentialsProvider
import utils.auth.{ JWTEnvironment, WithProvider }

import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }

/**
 * The `Change Password` controller.
 */
class ChangePasswordController @Inject() (scc: SilhouetteControllerComponents)(implicit ex: ExecutionContext) extends SilhouetteController(scc) {

  case class ChangePasswordModel(oldPassword: String, newPassword: String)

  implicit val changePasswordFormat: OFormat[ChangePasswordModel] = Json.format[ChangePasswordModel]

  /**
   * Changes the password.
   */
  def changePassword = SecuredAction(WithProvider[AuthType](CredentialsProvider.ID)).async {
    request: SecuredRequest[JWTEnvironment, AnyContent] =>
      implicit val lang: Lang = supportedLangs.availables.head
      request.body.asJson.flatMap(_.asOpt[ChangePasswordModel]) match {
        case Some(changePasswordModel) =>
          val credentials = Credentials(request.identity.email, changePasswordModel.oldPassword)
          credentialsProvider.authenticate(credentials).flatMap { loginInfo =>
            val newHashedPassword = passwordHasherRegistry.current.hash(changePasswordModel.newPassword)
            authInfoRepository.update(loginInfo, newHashedPassword).map(_ => Ok)
          }.recover {
            case _: ProviderException => BadRequest(JsString(messagesApi("invalid.old.password")))
          }
        case None => Future.successful(BadRequest(JsString(messagesApi("invalid.body"))))
      }

  }
}
