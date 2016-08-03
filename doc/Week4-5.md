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
