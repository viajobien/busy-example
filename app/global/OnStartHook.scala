package global

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import com.viajobien.busy.dsl.condition.{Always, Header, Is}
import models.Route
import repositories.RouteRepository
import services.RouteService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * @author david on 24/11/16.
  */
@Singleton
class OnStartHook @Inject()(system: ActorSystem, routeService: RouteService, routeRepository: RouteRepository)
                           (implicit executionContext: ExecutionContext) {

  val alwaysCond = Always()
  // This is the same condition but parse from string.
  // This could be useful for binding if yor implement some CRUD for manage the routes.
  val alwaysCondParsed = Route.readCondition("always")

  val headerCond = Header(Some("provider"), Is("ipinfo"))
  val headerCondParsed = Route.readCondition("""header name "provider" is "ip-api" """)

  // There are conditions for json request body and query parameters

  val futureRoutesInitialized = Future.sequence(List(
    this.routeRepository.save(Route(1, "/photos", "https://jsonplaceholder.typicode.com/photos", alwaysCond)),
    this.routeRepository.save(Route(2, "/photos/*", "https://jsonplaceholder.typicode.com/photos/*", alwaysCondParsed)),
    this.routeRepository.save(Route(3, "/ip/*", "http://ipinfo.io/*", headerCond)),
    this.routeRepository.save(Route(4, "/ip/*", "http://ip-api.com/json/*", headerCondParsed))
  ))

  Await.ready(this.futureRoutesInitialized, Duration.Inf)
  Await.ready(this.routeService.loadRoutes, Duration.Inf)

}
