# 5.3 - Implicit Parameters
implicit keyword에 대한 설명
http://blog.seulgi.kim/2014/09/scala-implicit-keyword-0.html
- implicit converter
 * value를 적절한 type으로 암묵적(implicit)으로 변환.
 * class 내부에 선언되어야 하고, class간 implcit conversion을 위해서는 특정 class에 해당 내용을 추가해 주어야 함.
 * 외부 lib에 있는 class간 변환이라면 별도의 wrapper 클래스 구현해야함.
 * class 외부에 정의된 implicit converter는 개발자가 파악하기 힘든 문제.

- implicit class
 * 이미 존재하는 class에 새로운 함수를 추가하기 위해 사용
 * 원래 class는 바꾸지 않고, class의 implicit conversion을 통해 호출할 함수 추가.
 * implicit class는 내부적으로 class 명과 동일한 implicit converter를 추가하는 방식으로 동작한다. (implicit converter의 문법적 편의성 제공)
 * 제약조건
   > implicit class는 trait/class/object 내부에 정의되어야 함.

   > non-implicit인 pararameter가 반드시 1개인 constructor가 있어야 함.

   > implicit class가 선언된 scope 내부에는 같은 이름의 어떤것도 있으면 않됨.

 - implicit parameter
  * 함수를 호출할 때 인자를 생략할 수 있도록 하는 역할
  * 구체적으로 말하면, 함수를 호출할때 생략된 인자를 자동으로 추가해 주는 것.
  * 인자를 무엇으로 추가할지 정하는 규칙
   > 1 규칙. 함수가 호출된 scope에 prefix 없이 접근할 수 있는 implicit parameter같은 변수 중, implicit label이 붙은 변수 사용. (implicit parameter, implicit modifier가 있는 local 변수)

   > 2 규칙. Companion object에 정의된 변수 중 implicit parameter로 선언된 변수 중 implicit label이 붙은 변수 사용.


## Making Sort more general
이전 예제에서 list[Int]를 sort하기 위한 msort함수를 Int 이외의 파라미터를 사용할 수 있도록 할 수 있을까?
```
def msort[T](xs: List[T]): List[T] = ...
```
아마도 동작하지 않을 것이다. 왜냐하면 merge함수에서 < (비교 연산자)가 임의 Type인 T를 위해서 정의되지 않았기 때문이다.

그럼, merge 함수를 parameterized하여 필요한 비교연산자를 선언하면 되지 않을까?

```
def msort[T](xs: List[T]): List[T] = {
  val n = xs.length/2

  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if(x < y) x :: merge(xs1, ys)  // <- 에러
        else y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}
```
Type parameter를 사용하여 다양한 값을 처리할 수 있도록 코드를 변경하였다.

그런데 "x < y"에서 "can not resolve symbol <" 오류가 발생한다.

< 연산자가 모든 T type에 존재하지 않기 때문..
어떻게 해결할까?

## Parameterization of Sort
가장 유연한 방법은 msort함수를 다형성을 지원하도록(polymorphic)하여 비교연산자(<)를 paramter로 전달할 수 있도록 하는 것이다.
```
def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {  //비교 연산자 전달
 val n = xs.length/2
 if (n == 0) xs
 else {
   def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
     case (Nil, ys) => ys
     case (xs, Nil) => xs
     case (x :: xs1, y :: ys1) => {
       if(lt(x, y)) x :: merge(xs1, ys) //전달받은 비교연산자 적용
       else y :: merge(xs, ys1)
     }
   }
   val (fst, snd) = xs splitAt n
   merge(msort(fst)(lt), msort(snd)(lt))
 }
}

val nums = List(2, -4, 5, 7, 1)
msort(nums)((x: Int, y: Int) => x < y)
msort(nums)((x, y) => x < y) // 별도 type 표기를 하지 않아도 compiler가 nums Type을 기반으로 추론함.

val fruits = List("apple", "pineapple", "orange", "banana")
msort(fruits)((x: String, y: String) => x.compareTo(y) < 0)
```

## Parameterization of Ordered
표준 library에 존재하는 scala.math.Ordering[T] class를 비교연산자로 전달해 보자. (직접 구현한 lt 함수 대신)
```
import math.Ordering
def msort[T](xs: List[T])(ord: Ordering[T]): List[T] = {  //비교 연산자 전달
val n = xs.length/2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if(ord.lt(x, y)) x :: merge(xs1, ys) //전달받은 비교연산자 적용
        else y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst)(ord), msort(snd)(ord))
  }
}
val nums = List(2, -4, 5, 7, 1)
msort(nums)(Ordering.Int)
```

## implicit parameter
lt 또는 Odering 변수를 전달하는 것은 번거로운 작업이므로,
Ordering 변수를 implicit parameter로 만들어서 단순화 해보자.
```
import math.Ordering
def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {  //implicit parameter로 변환
val n = xs.length/2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if(ord.lt(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}
val nums = List(2, -4, 5, 7, 1)
msort(nums) //implicit parameter로 정의되어 있는 ordering 파라미터 생략
```

## Rules for implicit parameter
만약 function이 implicit parameter (Type이 T)을 취한다면,
compiler는 implicit에 대한 정의를 아래의 조건에서 찾을 것이다.
- implicit 이라고 마크되어 있는 곳
- T와 호환이되는 type을 가지고 있는 곳
- function을 호출하는 scope에서 접근이 가능한 영역 또는 T와 관련된 companion object로 정의된 경우
 * 예를 들어, merge(msort(fst), msort(snd))에서 implicit parameter인 ord는 visible(접근가능?)하다.
 * 반면에 msort(nums)에서 전달되었던 Orderings은 Ordering class의 companion object이므로 implicit argument로 판단한다.

## Exercise : implicit Parameters
 merge(msort(fst), msort(snd)) 여기에서는 어떤 implicit argument가 삽입되었을까?
 - Ordering.Int
 - Ordering.String
 - the "ord" parameter of "msort"
정답은 3번 (ord)
