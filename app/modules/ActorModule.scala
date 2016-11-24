package modules

import com.google.inject.AbstractModule
import com.viajobien.busy.actors.ProducerActor
import play.api.libs.concurrent.AkkaGuiceSupport

/**
  * @author david on 24/11/16.
  */
class ActorModule extends AbstractModule with AkkaGuiceSupport {

  override def configure() {
    bindActor[ProducerActor](ProducerActor.name)
  }

}
