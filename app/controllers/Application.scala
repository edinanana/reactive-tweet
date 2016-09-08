package controllers

import play.api.mvc.{Action, Controller}


/**
  * Created by edinakim on 2016. 8. 30..
  */
object Application extends Controller {
  def index = Action { implicit request =>
    // Response
    Ok("hello world")
  }
}
