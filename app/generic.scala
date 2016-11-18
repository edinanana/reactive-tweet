import shapeless.HNil

/**
  * Created by edinakim on 2016. 10. 20..
  */
/**
object generic {
  case class Employee(name:String, number:Int, manager: Boolean)
  case class IceCream(name:String, numCherries:Int, inCone: Boolean)

  def employeeCsv(e: Employee): List[String] =
    List(e.name, e.number.toString, if(e.manager) "yes" else "no")

  def iceCreamCsv(c: IceCream): List[String] =
    List(c.name, c.numCherries.toString, if(c.inCone) "yes" else "no")

  import shapeless._
  val genericEmployee = Generic[Employee].to(Employee("edina", 12, false))
  val genericIceCream = Generic[IceCream].to(IceCream("edinanana", 0, false))

//  def genericCsv(gen: String :: Int :: Boolean :: HNil): List[String] =
//    List[String](gen(0), gen(1).toString, true)
}

// ADT 에서 'and types'는 products = 교집합
// 'or types'는 coproducts = 합집합

type cheese1 = Either[String, Int]  // coproducts
type cheese2 = (String, Int)  // products


sealed trait Soju
case class `자몽에이슬`(`자몽`: Int, `이슬`: Int) extends Soju
// 소주 ( 자몽에이슬 || 산사춘) => coproducts
// 자몽에이슬 ( 자몽 && 이슬) => products

// HList : 다른 종류의 type 을 담을 수 있음
val any: List[Any] = List("string", 1, true)  // 타입을 잃어버림. List는 기본적으로 같은 타입
// HList 로 서로 다른 타입의 값을 넣어도 그 타입을 그대로 유지함 (type safe)
val hlist = "str" :: 1000 :: false :: HNil

val hlist2 = 10000 :: hlist


// 같은 타입의 파라미터를 받는 클래스가 여러개가 있을때, csv 를 만드려면 중복된 코드가 여러개 생산됨
// 제네릭 타입으로 만들면 한벌만 만들어 두루 사용할 수 있음
// case class 는 정해진 타입만 받을 수 있음
// HList 는 느슨한 타입
// Generic :: case class     =>     HList      =>
//          다른 case class 를 => 같은 HList 로 바꿈

**/