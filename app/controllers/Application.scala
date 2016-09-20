package controllers

import com.ning.http.client.oauth.{ConsumerKey, RequestToken}
import core.Env
import play.api.mvc.{Action, Controller}


/**
  * Created by edinakim on 2016. 8. 30..
  */
object Application extends Controller {
  import core.Env._

  def index = Action { implicit request =>
    // Response
    Ok("hello world")
  }

  case class ConsmerKey(apiKey: String, apiSecret: String)
  case class RequestToken(token: String, tokenSecret: String)

  def credential: Option[(ConsmerKey, RequestToken)] = {
    val apiKey: Option[String] = Env.as[String]("twitter apiKey")
    val apiSecret: Option[String] = as[String]("twitter apiSecret")
    val token: Option[String] = as[String]("twitter token")
    val tokenSecret: Option[String] = as[String]("twitter tokenSecret")


    // 1개라도 값이 없을 경우 None 반환
    // 모두 4개의 값이 모두 Some => Some((ConsumerKey, RequestToken)) 반환

    // 1. option에서 값을 꺼내서 option(tuple)로 변환
    // 1-1. flatMap (Option[A] => f:A => Option[B])
    val res: Option[(ConsmerKey, RequestToken)] = apiKey.flatMap {
      ak => apiSecret.flatMap {
        as => token.flatMap {
          t => tokenSecret.flatMap {
            ts => Some(ConsmerKey(ak, as), RequestToken(t, ts))
          }
        }
      }
    }
    res

    // 1-2. for
    val resFor: Option[(ConsmerKey, RequestToken)] = for {
      ak <- apiKey
      as <- apiSecret
      t <- token
      ts <- tokenSecret
    } yield (ConsmerKey(ak, as), RequestToken(t, ts))
    resFor

    // 1-3. match
    val resMatch: Option[(ConsmerKey, RequestToken)] = (apiKey, apiSecret, token, tokenSecret) match {
      case (Some(ak), Some(as), Some(t), Some(ts)) => Some(ConsmerKey(ak, as), RequestToken(t, ts))
      case _ => None
    }
    resMatch
  }
}
