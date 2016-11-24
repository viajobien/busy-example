package api

import javax.inject.{Inject, Named}

import akka.actor.ActorRef
import com.viajobien.busy.actors.ProducerActor
import play.api.Logger
import play.api.libs.json.{JsNull, Json}
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import repositories.RouteRepository
import services.RouteService

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author david on 24/11/16.
  */
class CustomRouter @Inject()(@Named(ProducerActor.name) producerActor: ActorRef,
                             routeService: RouteService, routeRepository: RouteRepository
                            )(implicit ec: ExecutionContext) extends SimpleRouter {

  override def routes: Routes = {
    case _ =>
      Action.async { implicit request =>
        val future = this.routeService.route(
          futureJson => futureJson map {
            case JsNull => ServiceUnavailable(Json.obj("error" -> "Error on access service"))
            case json   => Ok(json)
          },
          () => Future.successful(BadGateway(Json.obj("error" -> "route not found")))
        )

        future recover {
          case e =>
            Logger.warn("error on getRoute", e)
            ServiceUnavailable(Json.obj("error" -> "error calling route"))
        }
      }
  }

}
