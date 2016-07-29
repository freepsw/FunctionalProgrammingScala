object test {
  def toList[A](a: A) = List(a)

  trait List[T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
  }

  class Cons[T](val head:T, val tail:List[T]) extends List[T] {
    def isEmpty = false
  }

  class Nil[T] extends List[T] {
    def isEmpty: Boolean = true
    def head: Nothing = throw new NoSuchElementException("Nil.head")
    def tail: Nothing = throw new NoSuchElementException("Nil.tail")
  }

  def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
  singleton[Int](1)
  singleton[Boolean](true)
  singleton(1)  // 이와 같이 Type parameter를 생략해도 type추론 가능.
  /*
  Types and Evaluation
  - Type 정보는 compile 시점에서만 유효함.
  - runtime 시점에서는 type 정보를 유지하지 않음.
  - 일부 C++, C#, F#에서만 runtime 시점에 type을 유지.
  */


  val s1 = "c body"
  val s2 = "b body"
  val b = s1 > s2


}



// variance annotation 의미
/*
http://jetzt.tistory.com/927 && http://changsuk.me/?p=2031
0. Scala에서 Type Variance의 필요성
 - Polymorphism (다형성) :
   * Type정보를 파라미터로 전달하여 I/F의 재사용을 높임.
   * 하나의 I/F가 여러 Type을 지원하여 Code 응집도/복잡도 개선 및 유지보수성 향상
   * Compile 시점에 Type Parameter의 유효성을 검증함(Type 추론 활용)
 - Generic Class
   * Class에 Type Parameter를 지정하여 Generic Class 선언 가능
     =>
      class Stack[T] {
        var elems: List[T] = Nil
        def push(x: T) { elems = x :: elems }
        def top: T = elems.head
        def pop() { elems = elems.tail }
      }
   * Generic Class에서는 Type Parameter T에 대한 Instance가 생성되면
   * 지정된 Type이외의 Type은 저장될 수 없다.
   * 즉 위의 Stack[Char]에서 Char Type 이지만 정수형으로 처리되고 있는 문제발생.
   * 실제 Runtime시에 Char의 범위를 넘어서는 Int를 입력하면 오류 발생함.
 - Type variance는 위의 Type Parameter의 불변성 제약을 해결하기 위해 도입됨.

1. Type Variance
 - variance (가변성)
  * 타입이 매개변수화된 타입 A(Scala에서 타입은 trait이나 class로 정의된다)와,
  * A의 타입 파라미터를 콘크리트하게 선언한 타입 B의 관계
    => val x: X[A] = new X[B]

 - Type parameter T에 대하여
  * 공변적 (covariant) : S(B)가 T(A)의 서브타입이면 Queue[S] subtype of Queue[T], 표기 : class Queue[+T]
    => "순가변자"로도 표현, Type parameter "T"가 하위 타입을 포함하도록 설계 -> T의 하위 호환범위 넓여줌.
    => 예
      class X[+T]
      val x: X[String] = new X[Nothing]   // legal
      val x: X[String] = new X[Object]    // illegal (Object는 String의 서브thpe이 아님.)
    => A의 Type parameter인 T를 covariant로 선언[+T]하면, B에서는 T의 하위 타입을 사용가능
  * 반공적 (contravariant) : T가 S의 서브타입이면 Queue[T] subtype of Queue[S], 표기 : class Queue[-T]
    => "역가변자"로도 표현, "T"가 상위 타입을 포함하도록 설계,
    => class Y[-T]
       val y: Y[String] = new Y[Nothing]   // illegal (A-String이 상위에 B-Nothing이 존재하지 않음)
       val y: Y[String] = new Y[Object]    // legal
  * Default로 scala의 Type Parameter는 무공변(nonvariant)
    => 따라서 compiler가 해당 sub type이 건전한지 체크할 수 있도록, variance annotation 추가 해야함.
    => java는 Runtime 시점에 원소의 type을 지정하여, Type이 맞지 않으면 Error를 발생시킴.

2. Type Boundaries
 - why : 타입 가변성은 타입 파라미터가 허용하는 타입의 범위를 확장시켜주지만, 가변성 만으론 범위를 제한할 수 없다.
         따라서 해당 타입의 한계점을 설정해줘야만 할 때가 있다
 - 정의 : type variance와 별도로 type parameter의 범위를 제약 (상위경계, 하위경계)
 - 상위 타입경계 : Type Parameter "T'가 반드시 Type A의 하위인 경우
   * T <: A => Java에서 T extends A와 동일 (즉 T는 A의 서브타입)
 - 하위 타입경계 : Type Parameter "T'가 반드시 Type A의 상위인 경우
   * T >: A => Java는 표기법이 있나?

*/
