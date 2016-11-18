
import shapeless._

// 1번 퀴즈
// HList 를 만드는데 string, int, boolean
// 이중에서 두번째 int의 값을 가져와봅니다
// => type을 유지한다. int 형으로 나옴
val h = "hello" :: 20 :: true :: HNil
println(h.tail.head)

// 2번 퀴즈
// tuple 로도 만들어 봅니다
// => type을 유지한다. int 형으로 나옴
val tuple_h = ("hello", 20, true)
println(tuple_h._2)

// 3번 퀴즈
// list 로도 만들어 봅니다
// => type을 유지하지 못함 any 타입이 됨
val list_h = "hello" :: 20 :: true :: Nil
println(list_h.tail.head)

case class Employee(name:String, number:Int, manager: Boolean)
case class IceCream(name:String, numCherries:Int, inCone: Boolean)

def employeeCsv(e: Employee): List[String] =
  List(e.name, e.number.toString, if(e.manager) "yes" else "no")

def iceCreamCsv(c: IceCream): List[String] =
  List(c.name, c.numCherries.toString, if(c.inCone) "yes" else "no")

val e = Employee("edina", 1, true)
val i = IceCream("vera", 0, false)

employeeCsv(e)
iceCreamCsv(i)

val edinaGeneric = Generic[Employee].to(e)
val iceGeneric = Generic[IceCream].to(i)

def toCsv(sib: String :: Int :: Boolean :: HNil): List[String] =
List(sib.head, sib.tail.head.toString, sib.tail.tail.head.toString)


// 관심사의 분리
