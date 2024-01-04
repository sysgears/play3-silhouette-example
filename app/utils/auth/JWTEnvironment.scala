package utils.auth

import models.User
import play.silhouette.api.Env
import play.silhouette.impl.authenticators.JWTAuthenticator

/**
 * The JWT environment.
 */
trait JWTEnvironment extends Env {
  type I = User
  type A = JWTAuthenticator
}
