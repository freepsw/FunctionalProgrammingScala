# 4.3 Subtyping and Generics
## Polymorphism
Polymorphism에는 2가지 중요한 요소가 있다.
- Subtyping
- Generics

이번 세션에서는 그 2가지 요소간의 상호작용을 다룰 것이다
2개의 메인 영역은
 - bounds
 - variance

## Type bounds
assertAllPos라는 메소드를 가정했을때
- takes an Inset
- 만약 모든 element가 positive라면 IntSet자체를 반환
- 그 외의 경우 throw exception
assertAllPos에 어떤 type이 최적일까?
아마도 아래와 같이 생각할 것이다
```
def assertAllPos(s: IntSet): IntSet
```
대부분에는 적절하다, 하지만 좀 더 정교하게 할 수 있을까?
```
assertAllPos (Empty) = Empty
assertAllPos(NonEmpty(...)) = NonEmpty(...)
assertAllPos(NonEmpty(...)) = throw exception
```
Empty, NonEmpty을 다 지원할 수 있는 파라미터가 필요하다.

- 이러한 type parameter를 표현하는 방법은
```
S <: T // S is a subtype of T , upper bounds
S >: T // S is a supertype of T , lower bounds
ex) [S >: NonEmpty]
    -> NonEmpty의 상위 class를 모두 지원 가능, IntSet, AnyRef, Any 등
```

##  Mixed bounds
```
[S >: NonEmpty <: IntSet]
```
이 경우에는 NonEmpty ~ IntSet에 위치하는 class만 지원가능 하도록 제약

## Covariance (공변성)
Subtyping과 type parameter간에는 또다른 상호작용이 있다.
```
 NonEmpty <: IntSet
 는 아래의 의미와 동일하다.
 List[NonEmpty] <: List[IntSet]
```
이러한 관계를 covariance를 가지고 있다고 한다. 왜냐하면 그들의 subtyping 관계가 type parameter에 따라 다양하기 때문이다. 위의 예시에서는 List의 type parameter를 covariance type으로 정의하는 것이 좋다.

## Arrays
Java or C#의 관점에서 보면
- T elements는 T[]로 표현
Scala에서는
- parameterized type syntax로 표현하여 Array[T]로 표현

Java에서 Array는 covariant이다.

- NonEmpty [] <: IntSet []

## Array Typing Problem
하지만 covariant array 타이핑은 문제를 야기한다.  왜냐면 아래의 java code를 보자.
```
(1) NonEmpty [] a = new NonEmpty [] {
  new NonE(1, Empty, Empty)
}
(2) IntSet [] b = a  // java에서는 covariant로 인식함.
(3) b[0] = Empty
(4) NonEmpty s = a[0]
```
마지막 line에서 Emtpy 를 NonEmpty에 할당 한것으로 보인다. 문제가 없을까?
순서대로 보자.
1. a -> NE (초기에 NonEmpty를 할당)
2. b -> a (a와 b가 동일한 NonEmpty 객체를 참조함.)
3. b -> Empty (b가 참조하는 객체를 NonEMpty -> Empty로 변경, 이떄 a가 바라보는 객체도 Empty로 바뀌게됨.)
              // RunTime시에 ArrayStoreException이 발생하게됨.
              // Empty를 Array b에 할당하는 것을 막기 위한 Exception
4. s -> a[0] (새로운 NonEmpty 객체 s에 a[0]-Empty를 할당하려고 함.)
왜 이런 문제가 발생할까?
java에서 new NonEmptyp[]{new ...}는 기본으로 NonEmpty Array를 생성한다고 가정하게 된다.  
그래서 Type tag에 NonEmpty를 표시하게 되고, Java RunTime 시에 Array에 해당 Type이 저장되는지 체크하게 된다.
그런데 위의 예시에서는 Empty를 저장하려고 하면서, Runtime시에 Exception이 발생하게 되는 것이다.
그럼 왜 java를 설계한 사람들은 이러한 오류를 유발하게 하는지 논쟁의 여지가 있을것이다.
java측의 입장은 sort(Ojbect[] a)와 같은 메소드를 구현할때 Object를 상속받은 모든 Array를 전달가능 하도록 만들려는데 있었다고 한다. (java 초기버전)
java1.5이후에는 generic type이 지원되었으나, 기존 설계를 변경할 수 없었음.

* Scala에서는 위의 예시를 compile하면 2 line에서 compile 에러가 발생한다.
 - a는 Array [NonEmpty]
 - b는 Array[IntSet]
 - NonEmpty -> IntSet (IntSet을 상속받지만, Covariant가 아니므로 casting시 Type 오류가 발생함.)

# 4.4 Variance (Optional)
## Variance
List는 immutable, Array는 mutable(값을 update해야 함.)
넓게 말하자면, elements가 변경될수 있는 mutable type은 covariant가 되어서는 안된다.
하지만, immutabl type은 어떤 조건과 method가 맞으면 covariant로 되기 쉽다.
이번 강의에서는 covariant가 되기 위한 구체적인 조건과 언제 type이 covariant가 될수 있는지와 언제 될수 없는지를 알아볼 것이다.

## Definition of Variance
C[T]를 paramaterized type이라고 하고, A,B를 A <: B 관계인 type으로 정의해보자. (A is subtype of B)
일반적으로 C[A]와 C[B]는 3가지 관계가 발생한다.
```
C[A] <: C[B] // C is covariant
C[A] >: C[B] // C is contravariant
아무관계도 아닌 경우 // nonvariant
```
Scala에서 표기방식은
```
class C[+A] {...} //covariant, A type 하위객체만 저장가능
class C[-A] {...} //contravariant, A type 상위객체만 저장가능
class C[A] {...} //nonvariant, A type만 저장가능
```

- Exercise
```
type A = IntSet => NonEmpty
type B = NonEmpty => IntSet
```
 - A와 B의 관계는 어떻게 표현될까?
 - 정답 : A <: B
 - why?
  * type A는 NonEmpty을 생성
  * type B는 IntSet를 생성
  * B가 좀 더 다양한 조건을 충족하는 type임. A는 NonEmpty만 return하는 한계

- Function에 대한 rule
```
If A2 <: A1 and B1 <: B2
then
  A1 => B1 <: A2 => B2
```
위의 code가 직관적으로 이해되는가? (그림으로 그려서 설명해줌)
```
A2 => B2
ㅅ     V  (위 아래 화살표....ㅎ)
A1 => B1
```
A2는 A1의 하위 type이고, return값으로 B1의 상위 type을 return하게된다.
결과적으로 A2 => B2가 더 많는 조건들을 만족시키므로,
A1 => B1 <: A2 => B2 로 표현되는 것이다.

## Function Trait Declaration
Function은
argument type에서는 contravariant이고,  //많은 상위 type을 지원해야 하고,
result type에서는 covariant이다.        //많은 하위 type을 지원해야 한다. (의미가 맞나?)
위 정의는 Function1 trait에 대하여 아래와 같은 개선을 이끌어낸다.
```
package scala
trait Function1 [-T, +U] {
  def apply(x: T): U
}
```

## Variance Checks
대략적으로
- covariant type parameter는 method result에서만 나타나고,
- contravariant type parameter는 method parameter에서만 나타난다
- nonvariant는 어디서나 나타날수 있다.

Exercise
```
trait List[+T] {
  def prepend(elem: T): List[T] = new Cons(elem, this)
}
```
왜 위의 code는 non type-check 에러가 발생하는가?
So what we get is an error, which says "covariant type T occurs in contravariant position in type T of value elem."
위의 variance check에서 정의한 Rul에 따르면 method parameter에서는 contravariant만 나타나게 되어 있지만, prepend에서는 covariant인 T을 입력값으로 설정하였다.
이는 LSP를 위반하기 때문이다.

예를 들어보자
List[IntSet]의 type인 xs에서 xs.prepend(Empty)를 실행하면 정상수행된다.
Empty는 IntSet의 하위 type이므로 문제가 없음.
그런데.
List[NonEmpty]의 type인 ys에서 ys.prepend(Empty)를 실행하면 에러가 발생한다.
 - type mismatch
 - required: NonEmpty
 - found: Empty
이는 Empty는 NonEmpty의 하위 typd이 아니기 때문에 발생한다.
trait List[+T]로 선언했기 때문에 T인 NonEmpty 하위 class만 적용이 가능하다.

결과적으로 위 방식으로는 List[NonEmpty]이 List[IntSet]의 subtype이 될수 없다.  

## Lower bounds
하지만, prepend는 immutable list를 가지는 자연스로운 method이다.
어떻게 해야 variance-correct가 가능할까?

lower bound를 활용해 보자
```
def prepend [U >: T] (elem: U): List[U] = new Cons(elem, this)
```
variance check에서 에러가 발생하지 않는다. 왜냐하면
- covariant type parameter(T)는 method type parameter의 lower bound에 나타나고,
- contravariant type parameter(T)는 method의 upper bound에 나타난다.

내 생각대로 정리해 보면
 - 기존에는 T를 바로 prepend의 parameter로 전달하여 variance check에서 오류(method parameter는 contravariant만 입력되어야 함.)발생
 - 변경된 함수에서는 lower bound를 이용해 U를 정의하고, U를 method parameter로 전달하여 오류를 해결.
 - 여기서 U는 T의 super type이다.

 Exercise
 아래 함수의 결과는 무엇일까?
 ```
 def f(xs: List[NonEmpty], x: Empty) = xs prepend x  ?
 ```
 * 정답 : List[IntSet]
 * why?
  - prepend[U >: T] => 여기서 T는 NonEmpty, U는 T의 supertype (IntSet)
  - (elem: U) => 여기서 U는 Empty, 왜냐하면 xs prepend x에서 x는 Empty이며, elem은 x를 입력 파라미터로 받음.
  - T를 포함하는 supertype이 Empty인가? 아님.
  - scala에서는 추론을 통해 바로 상위단계 type인 IntSet을 U type으로 정의함.
