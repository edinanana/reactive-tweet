package core

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by edinakim on 2016. 9. 8..
  */
class EnvSpec extends FunSuite with Matchers {
  test("apikey") {
    import EnvParserInstance._    // 컴패니언 오브젝트로 이름이 EnvParser 이면 따로 import dksgownjeh ehla

    Env.as[Int]("apiKey") shouldBe Some(123)
    Env.as[String]("apiToken") shouldBe Some("abc")
    Env.as[Boolean]("hasKey") shouldBe Some(true)
  }
}
