package services

import javax.inject.{Inject, Singleton}

import com.viajobien.busy.services.Producer
import com.viajobien.busy.models.routing.{BasicRouter, EndpointRouter}
import play.api.libs.json.JsValue
import play.api.mvc.{AnyContent, Request}
import repositories.RouteRepository

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

// TODO: Maybe this could be part of busy project ??
/**
  * @author david on 24/11/16.
  *
  */
@Singleton
class RouteService @Inject() (producer: Producer, routeRepository: RouteRepository)
                             (implicit ec: ExecutionContext) {

  private val router: EndpointRouter = BasicRouter.getRouter(routeRepository)

  /**
    * Reload routes.
    */
  def loadRoutes = this.router.reload

  def route[T](successFunc: Future[JsValue] => T, errorFunc: () => T)(implicit request: Request[AnyContent]): T =
    this.router.route match {
      case Some(endpoint) =>
        val duration = 20 seconds // Just for test you probably need other timeouts
        val value = this.producer.produce(request, endpoint, duration)
        successFunc(value)

      case _ =>
        errorFunc()
    }

}
