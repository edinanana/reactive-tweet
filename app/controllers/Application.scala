package controllers


import core.Env
import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws.{WS, WSRequest}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global
import core.EnvParserInstance._
import play.api.Logger
import play.api.libs.iteratee.{Concurrent, Iteratee}
import play.api.libs.json.JsObject    // 컴패니언 오브젝트로 이름이 EnvParser 이면 따로 import dksgownjeh ehla


/**
  * Created by edinakim on 2016. 8. 30..
  */
object Application extends Controller {
  import core.Env._

  def index = Action.async { implicit request =>

    val loggingIterates = Iteratee.foreach[JsObject] { js =>
      Logger.info(js.toString())

    }

    // enumerator - source : 들어오는 데이터 (근원지)

    // enumeratee - flow : 데이터의 흐름

    // iteratee - sink : 데이터가 끝나는 지점 (목적지, 흡수)

    // source ===> flow ===> sink ---> source ---> sink

    //////////////////////////////////
    import play.api.libs.iteratee._
    import play.api.libs.json._
    import play.extras.iteratees._

    val (iteratee, enumerator) = Concurrent.joined[Array[Byte]]

    // 데이터가 들어왔을때 어떻게 하겠다
    val jsonStream: Enumerator[JsObject] = enumerator &> Encoding.decode() &> Enumeratee.grouped(JsonIteratees.jsSimpleObject)

    // 데이터를 어떻게 소비하겠다
    jsonStream run loggingIterates

    // action(행위) : 실행을 통해서 이루어진 결과를 얻을 수 있다
    // definition(정의) : 실행전의 프로그램

    // iteratee ===> enumerator ===> jsonStream ===> loggingIteratee

    // stream 을 위해 iteratee, enumerator 을 사용
    //////////////////////////////////


    credential().map{
      case (ck: ConsumerKey, rt: RequestToken) =>
      WS.url("https://stream.twitter.com/1.1/statuses/filter.json")
        .sign(OAuthCalculator(ck, rt))
        .withQueryString("track" -> "박보검")
        .get { response =>
          Logger.info("response status: " + response.status)
          iteratee
        }
        .map{ _ => Ok("Stream closed")}
    }.getOrElse {
      Future.successful {
        InternalServerError("Twitter credential")
      }
    }
  }
//
//  case class ConsmerKey(apiKey: String, apiSecret: String)
//  case class RequestToken(token: String, tokenSecret: String)

  def credential(): Option[(ConsumerKey, RequestToken)] = {
    val apiKey: Option[String] = Env.as[String]("twitter.apiKey")
    val apiSecret: Option[String] = as[String]("twitter.apiSecret")
    val token: Option[String] = as[String]("twitter.token")
    val tokenSecret: Option[String] = as[String]("twitter.tokenSecret")


    // 1개라도 값이 없을 경우 None 반환
    // 모두 4개의 값이 모두 Some => Some((ConsumerKey, RequestToken)) 반환

    // 1. option에서 값을 꺼내서 option(tuple)로 변환
    // 1-1. flatMap (Option[A] => f:A => Option[B])
    val res: Option[(ConsumerKey, RequestToken)] = apiKey.flatMap {
      ak => apiSecret.flatMap {
        as => token.flatMap {
          t => tokenSecret.flatMap {
            ts => Some(ConsumerKey(ak, as), RequestToken(t, ts))
          }
        }
      }
    }
    res

    // 1-2. for
    val resFor: Option[(ConsumerKey, RequestToken)] = for {
      ak <- apiKey
      as <- apiSecret
      t <- token
      ts <- tokenSecret
    } yield (ConsumerKey(ak, as), RequestToken(t, ts))
    resFor

    // 1-3. match
    val resMatch: Option[(ConsumerKey, RequestToken)] = (apiKey, apiSecret, token, tokenSecret) match {
      case (Some(ak), Some(as), Some(t), Some(ts)) => Some(ConsumerKey(ak, as), RequestToken(t, ts))
      case _ => None
    }
    resMatch
  }
}
