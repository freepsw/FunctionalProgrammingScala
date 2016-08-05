# 5-4 Higher-Order List Functions
이번 코스에서는 implicit을 이용하지 않고, function을 argument로 넘겨서 msort를 구현해 보자.

## Recurring Patterns for Computations on Lists
지금가지 예시들을 보면 list에 있는 functions은 비슷한 구조를 가지고 있다.
몇 가지 반복되는 패턴을 보면
- list의 각 element를 변환한다. (특정 방식으로)
- 기준에 만족하는 모든 elements를 가져온다.
- operator를 활용하여 list의 elements를 결합한다.

Functional 언어는 프로그래머들이 high-order function과 같은 generic function을 작성할 수 있도록 지원한다.


## Apply a Function to Elements of a List
대부분의 operation은 list의 각 element를 변환하여 결과 list를 반환한다.
예를들면, 동일한 factor로 list의 각 element를 곱하는 것.
```
def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
  case Nil => xs
  case y :: ys => y * factor :: scaleList(ys, factor)
}
```

## Map
위 구조는 list class의 map 메소드를 이용해서 구현할 수 도 있다.
```
def scaleList(xs: List[Double], factor: Double): List[Double] = {
  xs map(x => x * factor)
}
```

## Exercise
각 element를 제곱하는 함수를 적용해서 map과 recursive 방식으로 구현해 보자.
```
def scaleList(xs: List[Double]): List[Double] = xs match {
  case Nil => Nil
  case y :: ys => y * y :: scaleList(ys)
}

def scaleList(xs: List[Double], factor: Double): List[Double] = {
  xs map(x => x * x)
}
```

## Filering
```
def posElems1(xs: List[Int]): List[Int] = xs match {
  case Nil => xs
  case y :: ys => if(y > 0) y :: posElems1(ys) else posElems1(ys)
}

def posElems(xs: List[Int]): List[Int] = {
  xs filter (x => x > 0)
}
```

filter 외에도 조건에 의해서 일부 list만 추출하는 메소드들이 있다.
- xs filterNot p : p함수에서 false를 반환하는 element만 list에 저장
- xs partition p : p함수에서 true인 list와 false인 list 2개의 list를 리턴한다.
```
scala> List(1,2,3,4).partition(x => x % 2 == 0)
res0: (List[Int], List[Int]) = (List(2, 4),List(1, 3))
```
- xs takeWhile p : list를 순회하면서 p조건에 맞는 부분까지만 반환한다. filter는 모든 elements를 전부 순회함.
```
List(1, 2, 3, -4, 5) takeWhile (_ > 0)
> res0: List[Int] = List(1, 2, 3)  // 5도 조건에 맞지만, 조건을 위반하는 순간 그 때 까지의 list만 반환함.
```
- xs dropWhile p : takeWhile과 유사하게 동작하며, 삭제하는 것.
- xs span p : span도 조건이 만족하는 순간까지 저장된 list와 나머지 list를 반환.
 * span과 유사한 함수들. sequence를 sublist로 분할하는 함수
 http://alvinalexander.com/scala/how-to-split-sequences-subsets-groupby-partition-scala-cookbook
```
val x = List(15, 10, 5, 8, 20, 12)
val y0 = x.groupBy(_ > 10)  //Map(false -> List(10, 5, 8), true -> List(15, 20, 12))
val y1 = x.partition(_ > 10) //(List(15, 20, 12),List(10, 5, 8))
val y2 = x.span(_ > 10) //(List(15),List(10, 5, 8, 20, 12))
val y3 = x.splitAt(2) //(List(15, 10),List(5, 8, 20, 12))
```

## Exercise
연속적으로 중복되는 element를 sub list로 반환하는 함수를 작성해보자.
```
def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (first, rest) = xs span (y => y == x)
    first :: pack(rest)
}
pack(List("a", "a", "a", "b", "c", "c", "a"))
//List(List("a", "a", "a"), List("b"), List("c", "c"), List("a"))
```

이번엔 연속으로 중복된 element의 발생횟수를 가지는 list(par(x, n))를 반환하는 함수.
```
def encode[T](xs: List[T]): List[(T, Int)] = {
  pack(xs) map (ys => (ys.head, ys.length))
}

encode(List("a", "a", "a", "b", "c", "c", "a"))
//List(("a", 3), ("b", 1), ("c", 2), ("a", 1))
```

# 5.5 Reduction of Lists
이번 코스에서는 forder or reduce combinators를 다룰 것이다.

list에서 일반적으로 제공하는 또다른 operation은 주어진 operator를 이용하여 elements를 결합하는 것이다.
예를 들면
```
sum(List(x1, ...., xn))      = 0 + x1 + ..... + xn
product(List(x1, ....., xn)) = 1 * x1 * ..... * xn
```
위 함수는 재귀방식을 통해서 구현할 수 있다.
```
def sum(xs: List[Int]): Int = xs match {
  case Nil => 0
  case y :: ys => y + sum(ys)
}
```

## FoldLeft
sum과 product 함수는 아래와 같이 정의될 수 있다.
```
def sum(xs: List[Int])     = (xs foldLeft 0)(_ + _)
def product(xs: List[Int]) = (xs foldLeft 1)(_ * _)
```

## Implementations of ReduceLeft and FoldLeft

## Difference between FoldLeft and FoldRight
아래의 코드는 2개의 list를 concat하는 함수이다.
```
def concat[T](xs: List[T], ys: List[T]): List[T] =
  (xs foldRight ys) (_ :: _)
```
- 만약 foldRight를 foldLeft로 변경하면 어떤 일이 발생할까?
- 정답은 컴파일 오류(::에서 발생, not a member of type parameter T)
 - Why?
 - 위의 코드를 좀 구체적으로 분해해 보면
 ```
 def concat[T](xs: List[T], ys: List[T]): List[T] = {
  (xs foldRight ys) ((x, y) => x :: y )
}
```
- 여기서 x는 T type이고, y는 List[T]로 정의되어 있다.
- (doc/~.pptx 41page 참조) foldRight의 작동방식이 도식화 되어 있음.
- 왼쪽(left, 여기서는 x-T)의 elements를 중앙의 operator를 통해 오른쪽(right, y- List[T], 실제는 하위 sub tree)과 combine 함.
- 따라서 실제 (_ :: _) => T :: List[T]로 해석되고,
- 4 :: List(1,2)과 같은 방식으로 실행된다.
- 4를 List(1,2)의 앞에 추가함.(4, 1, 2)

- 그런데 foldRight -> foldLeft로 변경하면?
- (xs foldLeft ys) ((x, y) => x :: y )
- foldRight와 반대로 왼쪽의 y가 T, x가 List[T]로 해석된다. (왜? foldLeft함수가 그렇게 처리하니까..)
- 따라서 x :: y는 List[T] :: T로 해석되고,
- List(1,2) :: 4 => 4.::(List(1,2))처럼 실행된다.
- 하지만 4(Int type)은 :: 연산자가 존재하지 않는다.
- 그래서 에러가 발생!!!! 아주 자세히 풀다보니까 좀 복잡하네..
