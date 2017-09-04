package controllers

import javax.inject.{Inject, Singleton}

import models.Route
import play.api.libs.json.Json
import play.api.mvc.{BaseController, BodyParsers, ControllerComponents}
import repositories.RouteRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author david on 24/11/16.
  */
@Singleton
class RoutesCtrl @Inject() (val controllerComponents: ControllerComponents, routeRepository: RouteRepository)
                           (implicit val ec: ExecutionContext) extends BaseController {

  def findAll = Action.async {
    this.routeRepository.findAll() map (routes => Ok( Json.toJson(routes) ))
  }

  def save = Action.async(parse.json) { request =>
    request.body.validate[Route].fold(
      _     => Future.successful(BadRequest( Json.obj("error" -> "invalid request") )),
      route => this.routeRepository.save(route) map (route => Ok( Json.toJson(route) ))
    )
  }

  def find(id: Long) = Action.async {
    this.routeRepository.findById(id) map {
      case Some(route) => Ok( Json.toJson(route) )
      case _           => NotFound
    }
  }

  def update(id: Long) = Action.async(parse.json) { request =>
    request.body.validate[Route].fold(
      _     => Future.successful(BadRequest( Json.obj("error" -> "invalid request") )),
      route => this.routeRepository.update(route) map (route => Ok( Json.toJson(route) ))
    )
  }

  def remove(id: Long) = Action.async {
    this.routeRepository.remove(id) map (if (_) Ok else NotFound)
  }

}
