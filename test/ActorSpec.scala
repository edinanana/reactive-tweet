import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import org.scalatest.{Matchers, WordSpec}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Created by edinakim on 2016. 11. 17..
  */

class MyActor extends Actor {
  override def receive: Receive = {
    case str: String =>
      println("@@@@@@@@@@@@ " + str)
    case int: Int =>
      println("!!!!!!!!!!! " + int)
    case bool: Boolean =>
      println("##################### " + bool)
      sender() ! s"i am boolean " + bool
    case _ => println("&&&&&&&&&&& ")
  }
}

class ActorSpec extends WordSpec with Matchers {

  // actor의 구조
  // ActorRef에 push -> | | | | -> pop (actor 에서 뒤에서부터 한번에 하나씩 pop)
  // ==> thread safe 하다

  "actor test" should {

    "my first actor" in {
      val system = ActorSystem()
      implicit val timeout = Timeout(5.seconds)
      var actorRef: ActorRef = system.actorOf(Props[MyActor])

      actorRef ! "martini"
      actorRef ! "edina"
      actorRef ! "kare"
      actorRef ! 1
      actorRef ! 2
      actorRef ! Some(2)

      // 응답을 받고 싶을 때는 ask 사용
      actorRef ask true
      actorRef ? false

      // ! 는 리턴타입이 없음
      // ? 는 리턴타입이 있음


      val future: Future[Any] = actorRef ? false
      future.foreach(println)

      val future1: Future[Any] = (actorRef ask true).mapTo[String]
      future1.foreach(println)
    }
  }
}
