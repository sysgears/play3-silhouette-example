package controllers

import models.services.UserService
import play.api.Logging
import play.api.http.FileMimeTypes
import play.api.i18n.{ I18nSupport, Langs, MessagesApi }
import play.api.mvc._
import play.silhouette.api.actions.{ SecuredActionBuilder, UnsecuredActionBuilder }
import play.silhouette.api.repositories.AuthInfoRepository
import play.silhouette.api.services.AuthenticatorService
import play.silhouette.api.util.{ Clock, PasswordHasherRegistry }
import play.silhouette.api.{ EventBus, Silhouette }
import play.silhouette.impl.providers.CredentialsProvider
import utils.auth.JWTEnvironment

import javax.inject.Inject

/**
 * Abstract silhouette controller which contains all components to work with authentication
 */
abstract class SilhouetteController(override protected val controllerComponents: SilhouetteControllerComponents)
  extends MessagesAbstractController(controllerComponents) with SilhouetteComponents with I18nSupport with Logging {

  def SecuredAction: SecuredActionBuilder[EnvType, AnyContent] = controllerComponents.silhouette.SecuredAction

  def UnsecuredAction: UnsecuredActionBuilder[EnvType, AnyContent] = controllerComponents.silhouette.UnsecuredAction

  def userService: UserService = controllerComponents.userService

  def authInfoRepository: AuthInfoRepository = controllerComponents.authInfoRepository

  def passwordHasherRegistry: PasswordHasherRegistry = controllerComponents.passwordHasherRegistry

  def clock: Clock = controllerComponents.clock

  def credentialsProvider: CredentialsProvider = controllerComponents.credentialsProvider

  def silhouette: Silhouette[EnvType] = controllerComponents.silhouette

  def authenticatorService: AuthenticatorService[AuthType] = silhouette.env.authenticatorService

  def eventBus: EventBus = silhouette.env.eventBus
}

/**
 * Contains silhouette components
 */
trait SilhouetteComponents {
  type EnvType = JWTEnvironment
  type AuthType = EnvType#A
  type IdentityType = EnvType#I

  def userService: UserService

  def authInfoRepository: AuthInfoRepository

  def passwordHasherRegistry: PasswordHasherRegistry

  def clock: Clock

  def credentialsProvider: CredentialsProvider

  def silhouette: Silhouette[EnvType]
}

/**
 * Contains silhouette components and messages api components
 */
trait SilhouetteControllerComponents extends MessagesControllerComponents with SilhouetteComponents

/**
 * Default Silhouette controller implementation
 */
final case class DefaultSilhouetteControllerComponents @Inject() (
  silhouette: Silhouette[JWTEnvironment],
  userService: UserService,
  authInfoRepository: AuthInfoRepository,
  passwordHasherRegistry: PasswordHasherRegistry,
  clock: Clock,
  credentialsProvider: CredentialsProvider,
  messagesActionBuilder: MessagesActionBuilder,
  actionBuilder: DefaultActionBuilder,
  parsers: PlayBodyParsers,
  messagesApi: MessagesApi,
  langs: Langs,
  fileMimeTypes: FileMimeTypes,
  executionContext: scala.concurrent.ExecutionContext
) extends SilhouetteControllerComponents
