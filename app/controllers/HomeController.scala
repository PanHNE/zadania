package controllers

import play.api.mvc._
import javax.inject._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc:MessagesControllerComponents)
                               (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index("Zadania"))
  }

}
