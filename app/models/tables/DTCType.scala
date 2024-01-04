package models.tables

import org.apache.pekko.http.javadsl.model.DateTime
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate

trait DTCType {
  implicit val localDateColumnType: BaseColumnType[LocalDate] =
    MappedColumnType.base[LocalDate, java.sql.Date](
      ld => java.sql.Date.valueOf(ld),
      sqlDate => sqlDate.toLocalDate
    )

  implicit val dateTimeColumnType: JdbcType[DateTime] with BaseTypedType[DateTime] =
    MappedColumnType.base[DateTime, java.sql.Timestamp](
      dt => new java.sql.Timestamp(dt.clicks()),
      ts => DateTime.create(ts.getTime)
    )
}
