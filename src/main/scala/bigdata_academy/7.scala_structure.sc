/*
 [implicit function]
 implicit는 예상되는 coding방식을 정해놓고,
 이를 개발자가 몰라도 사용할 수 있도록 하는것?
 아래의 예를 들면,
 IntWrapper에 있는 square함수를 Int객체에서 호출할 수 있도록 하고자 할 떄
 Int객체에 square 함수를 추가하는 것 보다는 implicit함수를 생성하여
 Int에서 square를 직접 호출하도록 보이게 하는 것
 처음 2 square를 호출하면 compiler에서 오류가 발생.
 하지만 compiler에서는 다시한번 implicit 함수를 조회하면서,
 Int를 parameter로 받는 함수가 있는지 확인한다.
 존재 한다면, 해당 함수를 호출

 아래와 같이 Int를 parameter로 받는 함수가 2개 있고,
 square도 동일하다면 square를 찾을 수 없다는 에러가 발생함.

*/

class IntWrapper (n: Int) {
  def square = n * n
}

class StringWrapper (n: Int) {
  def square2 = n + 10
}

implicit def toIntWrapper(n: Int) = new IntWrapper(n)
implicit def toStringWrapper(n: Int) = new StringWrapper(n)

2 square

/*
  Implicit parameter
  여기서는 parameter와 variable가 implicit으로 선언된 경우
  (implicit val name = "brandon" && (implicit s: String)

  parameter 없이 호출하여도, compiler에서 implicit parameter를 한번 더 찾아준다.

*/

implicit val name = "brandon"
implicit val age = 20

def printSomething (implicit s: String) = {
  println(s"name : $s")
}

def printSomething3 (implicit s: String, n: Int) = {
  println(s"name : $s, age : $age")
}

printSomething

printSomething3



def printSomething2 (implicit s: String) = {
  println(s"name : $s")
}

printSomething2


//companyon object는 new 연산자 없이 생성이 가능한 Singleton Object
class Person (val name: String, val age: Int)

object Person {
  def apply(name: String, age: Int) = new Person(name, age)
}

val p = Person.apply("Branson", 30)

p.name



2
