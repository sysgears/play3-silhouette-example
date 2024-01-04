package models

import org.apache.pekko.http.javadsl.model.DateTime
import play.api.libs.json.{ JsSuccess, Reads }

import java.time.LocalDate
import java.time.format.DateTimeFormatter

trait DTReader {
  implicit val dateTimeReader: Reads[DateTime] = Reads { json =>
    json.validate[String] map { str =>
      DateTime.fromIsoDateTimeString(str).orElseThrow(() => new Exception("Invalid date format"))
    }
  }
  implicit val localDateReader: Reads[LocalDate] = Reads { json =>
    json.validate[String].flatMap { str =>
      LocalDate.parse(str, DateTimeFormatter.ISO_DATE) match {
        case localDate: LocalDate => JsSuccess(localDate)
        case _ => throw new Exception("invalid date")
      }
    }
  }

}