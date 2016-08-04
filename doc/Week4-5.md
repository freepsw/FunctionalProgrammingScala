# 4.5 Decomposition
## Expressions
Expr이라는 trait를 상속받은 Number와 Sum class가 존재

## Evaluation of Expressions
```
def eval(e: Expr): Int = {
  if(e.isNumber) e.numValue
  else if (e.isSum) eval(e.leftOp) + eval(e.rightOp)
  else throw new Error("Unknown expression " + e)
}
```
function 내부에서 if else 구문을 통해 정의하게 되면, 새로운 구문이 추가될 경우 점점 복잡해진다.
```
class Prod(e1: Expr, e2: Expr) extends Expr
class Var(x: String) extends Expr
```
위와 같은 class가 추가될 경우 trait에 Prod를 구분할 수 있는 field와 function을 추가하고,
이를 상속받은 모든 class에도 해당 내용을 추가해야 한다.
```
def isVal
def isProd
def name // Var의 String 객체
```
이런 방식은 확장 및 수정에 아주 많은 시간이 소요된다.

## Non-Solution : Type Tests and Type Casts
type tests와 type casts를 활용하는 방안이다.
Scala는 Any class에 정의된 isInstanceOf[T]와 asInstanceOf[T]를 사용할 수 있다.
Java와 비교해 보면
Scala (x.isInstanceOf[T])  // type tests
Java  (x instanceof T)
Scala (x.asInstanceOf[T])
Java  ((T) x )

## Eval with Type Tests and Type casts
```
def eval(e: Expr): Int =
  if (e.isInstanceOf[Number])
    e.asInstanceOf[Number].numValue
  else if (e.isInstanceOf[Sum])
    eval(e.asInstanceOf[Sum].leftOp) + eval(e.asInstanceOf[Sum].rightOp)
  else throw new Error("Unknown expression " + e)
```
- 장점 : 별도의 분류(isNumber ...) 메소드 불필요
- 단점 : low-level 구현 및 unsafe 가능성

하지만 scala에서는 권장하지 않는다, 왜냐하면 더 좋은 대안이 있기 때문이다.
java와의 호환성을 위해서 사용하는 정도..


## Solution 1 : Object-Oriented Decomposition
```
trait Expr{
  def eval: Int
}

class Number(n: Int) extends Expr {
  def eval: Int = n
}

class Sum(e1: Expr, e2: Expr) extends Expr{
  def eval: Int = e1.eval + e2.eval
}
```
eval expression만 적용하여 정의함.
하지만, 만약 display method를 추가하려면 어떤 일이 발생할까?
아마도 모든 sub class에 해당 함수를 추가해야 할 것이다.

## Limitations of OO Decomposition
만약 계산식(expression)을 아래와 같이 단순하게 하려면 어떻게 해야 할까?
a * b + a * c ==> a * (b + c)
OO방식의 한계
 - OO 방식에서는 이를 지원할 수 있는 test(is...)와 access method를 별도로 추가


# 4.6 Pattern Matching
이전 방식을 요약해 보면
- Expr을 상속받은 4개의 class (Number, Sum, Prod, Var)
- classification(isNumber...)과 3개의 access method(eval, show, simplify)
- class별로 8개의 method가 정의됨(trait를  포함하여 총 40개)
- OO 방식으로 접근할 경우, new method를 추가하기 위해서 모든 class를 수정해야 함.

## Solution2. Pattern Matching을 이용한 Functional Decomposition
test 와 accessor function의 유일한 목적은 class의 생성자를 구분하기 위함이다.
- 예를 들면 어떤 subclass가 사용되었나?
- 또는 constructor의 argument는 무엇인가?
이러한 상황은 대다수 Functional language(scalal와 같은)에서는 아주 흔하게 발생하였고, Scala에서는 이를 자동화 하였다.

## Case Classes
```
trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
```
case class를 사용하여 위와 같이 정의하면, scala compiler에서는 아래와 같은 code를 생성하게 된다.
```
object Number {
    def apply(n: Int) = new Number(n)
}
object Sum {
  def apply(e1: Expr, e2: Expr) = new Sum(e1, e2)
}
```
일단 Companion object를 생성하여 apply method를 자동으로 추가한다.
위와 같이 정의함에 따라 new Number(1) 대신에  Number(1)을 직접 호출할 수 있다.  (Number.apply(1) 과 동일)
하지만 이 class들은 아무것도 정의되어 있지 않은 상태이다. 그러면 어떻게 member에 접근할 수 있을까?

## Pattern Matching
Java/C에서도 switch와 같은 기능이 있듯이, scala에서는 keyword 매칭을 통하여 처리한다.
```
def eval (e: Expr): Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}
```

## Forms of Pattern
Pattern은 아래 항목으로 구성된다.
- constructor (Number, Sum)
- variables (n, e1, e2)
- wildcard pattern ( _ )
- constants (1, true ...) // 상수는 대문자로 표기되록 정함.


## Pattern Matching and Methods
물론 위 예제의 eval function을 base trait의 method로 정의할 수 있다.
아래 예를 보면
```
trait Expr{
    def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }
}
```
this는 receiver object 자체를 의미함. e1.eval을 호출했다면 this는 e1이 됨.
trait에 eval을 정의할 것인지, Sum or Number와 같은 구체 sub class에 정의할 것인지는 취향 또는 알고리즘에 따라 다름.


## Exercise 1
String을 반환하는 show 함수를 작성하라.
```
 def show(e: Expr): String = e match {
   case Number(n) => x.toString
   case Sum(l, r) => show(l) + " + " + show(r)
 }
```

## Exercise 2 (좀 더 어려운..)
- "2 * x + y"와 같이 출력되도록 show함수를 작성하라.
- Sum(Prod(2, Var(”x”)), Var(”y”))  ==>
 - 도식화 해보면
 - Sum -> Prod + y
 - Prod -> 2 + x

- "(2 + x) * y"와 같이 출력
- Prod(Sum(2, Var(”x”)), Var(”y”))
 - 도식화 해보면
 - Prod -> Sum + y
 - Sum -> 2 + x


# 4.7 Lists
List와 Array(sequence)간에는 2가지 큰 차이가 있다.
- Lists는 immutable : list의 elements는 변경되지 않는다.
- Lists는 재귀적이지만, Array는 flat

## Constructor of Lists
모든 lists의 생성자는 empty list인 Nil로 시작하여 ::라는 생성자 연산자(cons라 발음)로 생성된다.
예를 들면
```
fruit = "apple" :: ("oragne" :: ("pears" :: Nil))
nums = 1 :: (2 :: (3 :: (4 :: Nil)))
```
docs에 첨부된 ppt(Cousera_Functional Programming parctice)의 그림을 보면 도식화된 list의 개념을 잘 알수 있다.
A :: B :: C는 A :: (B :: C)로 해석된다.

## Operation in Lists
head : List의 첫번째 요소
tail : first를 제외한 나머지 요소
isEmpty : empty여부 확인

fruit.head = "apples"
fruit.tail.head = "orange"

## List Patterns
Nil : Nil 상수
p :: ps : head가 p이고, tail이 ps인 List와 매칭
List (p1, ...., pn) : p1 :: .... :: pn :: Nil인 List와 매칭

## Exercise
x :: y :: Lists(xs, ys) :: zs인 List의 length를 가장 정확하게 match하는 조건은 무엇인가?
정답
 - x, y, Lists(xs, ys)는 구체적인 값이 있는 elements
 - zs는 Empty가 될 수 있으므로 length에서 제외
 - 따라서 Length는 최소 3개 이상.
