package controllers

import play.api.mvc._
import javax.inject._

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject() (cc:MessagesControllerComponents)
                               (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index("Zadania"))
  }

}
