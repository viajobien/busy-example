package repositories

import javax.inject.{Inject, Singleton}

import com.viajobien.busy.repositories.Repository
import models.Route

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author david on 24/11/16.
  */
@Singleton
class RouteRepository @Inject() (implicit val ec: ExecutionContext) extends Repository[Route] {

  /**
    * This is just for example, in production use a database.
    * Note that this work because this class is injected as a singleton.
    */
  private val map = scala.collection.mutable.HashMap.empty[Long, Route]

  def save(doc: Route): Future[Route] = {
    this.map += (doc.id -> doc)
    Future.successful(doc)
  }

  def update(doc: Route): Future[Route] =
    this.map.get(doc.id) match {
      case Some(route) => this.save(doc)
      case _           => Future.failed(new RuntimeException("Not found"))
    }

  def remove(id: Long): Future[Boolean] = {
    this.map -= id
    Future.successful(true)
  }

  def findById(id: Long): Future[Option[Route]] = Future.successful(this.map.get(id))

  def findAll(): Future[List[Route]] = Future.successful(this.map.values.toList)

  def count: Future[Int] = Future.successful(this.map.size)

}
