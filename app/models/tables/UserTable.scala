package models.tables

import models.User
import org.apache.pekko.http.javadsl.model.DateTime
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate

/**
 * Here we define the table. It will have a name of people
 */
class UserTable(tag: Tag) extends Table[User](tag, Some("play_silhouette"), "users") with DTCType {

  /** The ID column, which is the primary key, and auto incremented */
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc, O.Unique)

  /** The name column */
  def name = column[String]("name")

  /** The email column */
  def email = column[String]("email", O.Unique)

  /** The last name column */
  def lastName = column[String]("lastName")

  /** The password column */
  def password = column[Option[String]]("password")

  def dateOfBirth = column[LocalDate]("dateOfBirth")(localDateColumnType)

  def dateOfCreation = column[DateTime]("dateOfCreation")(dateTimeColumnType)

  /**
   * This is the table's default "projection".
   *
   * It defines how the columns are converted to and from the User object.
   *
   * In this case, we are simply passing the id, name, email and password parameters to the User case classes
   * apply and unapply methods.
   */
  def * = (id, email, name, lastName, password, dateOfBirth, dateOfCreation.?).<>((User.apply _).tupled, User.unapply)
}