package global

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.inject.ApplicationLifecycle

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * @author david on 24/11/16.
  */
@Singleton
class OnStopHook @Inject() (lifecycle: ApplicationLifecycle, system: ActorSystem) {

  lifecycle.addStopHook { () =>
    Future.successful {
      system.terminate()
      Await.result(system.whenTerminated, Duration.Inf)
    }
  }

}
