package models

import org.apache.pekko.http.javadsl.model.DateTime
import play.silhouette.api.util.PasswordInfo
import play.silhouette.api.{ Identity, LoginInfo }
import play.silhouette.impl.providers.CredentialsProvider
import play.silhouette.password.BCryptSha256PasswordHasher

import java.time.LocalDate

/**
 * The user object.
 *
 * @param id          The unique ID of the user.
 * @param lastName    the last name of the authenticated user.
 * @param password    the user's password
 * @param dateOfBirth the user's birth ()
 */
case class User(
  id: Option[Long],
  email: String,
  name: String,
  lastName: String,
  password: Option[String] = None,
  dateOfBirth: LocalDate,
  dateOfCreation: Option[DateTime]
) extends Identity {

  /**
   * Generates login info from email
   *
   * @return login info
   */
  def loginInfo = LoginInfo(CredentialsProvider.ID, email)

  /**
   * Generates password info from password.
   *
   * @return password info
   */
  def passwordInfo = PasswordInfo(BCryptSha256PasswordHasher.ID, password.get)
}

object User extends DTReader {

  import play.api.libs.json._

  implicit val userReads: Reads[User] = Json.reads[User]
  implicit val creatureWrites: Writes[User] = new Writes[User] {
    def writes(c: User): JsValue = Json.obj(
      "name" -> c.name,
      "lastName" -> c.lastName,
      "dateOfBirth" -> c.dateOfBirth.toString(),
      "email" -> c.email,
      "dateOfCreation" -> c.dateOfCreation.get.toString
    )
  }

}