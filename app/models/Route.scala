package models

import com.viajobien.busy.dsl.condition.Condition
import com.viajobien.busy.dsl.condition.ConditionDSL._
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath

/**
  * @author david on 24/11/16.
  */
case class Route(id: Long, override val path: String, endpoint: String, condition: Condition)
  extends com.viajobien.busy.models.routing.Route(path, endpoint, condition) {

  override type T = Long

}

object Route {

  def readCondition(string: String): Condition = string.parseCondition.get

  def writeCondition(condition: Condition): String = condition.toString

  implicit val format = (
    (JsPath \ "id").format[Long] and
    (JsPath \ "path").format[String] and
    (JsPath \ "endpoint").format[String] and
    (JsPath \ "condition").format[String].inmap(readCondition, writeCondition)
  )(Route.apply, unlift(Route.unapply))

}
