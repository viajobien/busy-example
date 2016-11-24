package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import com.viajobien.busy.actors.ProducerActor
import global.{OnStartHook, OnStopHook}

import scala.concurrent.ExecutionContext

/**
  * @author david on 24/11/16.
  */
class GlobalModule extends AbstractModule {

  override def configure() {
    /* *** BUSY bindings, not recommended to use ExecutionContext.global on production *** */
    bind(classOf[ExecutionContext])
      .annotatedWith(Names.named(ProducerActor.contextName))
      .toInstance(scala.concurrent.ExecutionContext.global)
    /* ********************* */

    bind(classOf[OnStartHook]).asEagerSingleton()
    bind(classOf[OnStopHook]).asEagerSingleton()
  }

}
