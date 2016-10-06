package actors

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, Props}
import play.api.Logger
import play.api.libs.json.Json

class TwitterStreamer(out: ActorRef) extends Actor {
  override def receive: Receive = {
    case "subscribe" =>
      Logger.info("Received message from a client")
      out ! Json.obj("text" -> "Hello world. actor 에서 보내요 ")
  }
}

object TwitterStreamer {
  def props(out: ActorRef) = Props(new TwitterStreamer(out))
}