package services

import javax.inject.{Inject, Named, Singleton}

import akka.actor.ActorRef
import akka.pattern._
import akka.util.Timeout
import com.viajobien.busy.actors.{Produce, ProducerActor}
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
class RouteService @Inject() (@Named(ProducerActor.name) producerActor: ActorRef, routeRepository: RouteRepository)
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
        implicit val timeout = duration: Timeout
        val value = (this.producerActor ? Produce(request, endpoint, duration)).mapTo[JsValue]
        successFunc(value)

      case _ =>
        errorFunc()
    }

}
