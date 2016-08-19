# 6.1 - Other Collections
이번 강의에서는 list 외에 다양한 collections을 다루어 보겠다.
## Other Sequences
- List는 선형으로 first element에 접근하는 것이 중간이나 끝에 있는 element에 접근하는 것보다 빠르다.
- Scala는 vector와 같은 sequence를 제공하며, vector는 list보다 균형있는 접근 패턴을 제공한다.
- Vector 구조를 보면, 처음 32개 element로 생성되고, 이를 다 사용하면 각 1개의 element를 다시 32개로 분할한다.
 - depth 1에서는 32개,
 - depth 2에서는 32 * 32개 공간 할당 - 2의 10승
 - depth 3에서는 2의 15승
 - depth 4에서는 2의 20승
 - depth 6에서는 2의 30승, 약 10억개의 elements를 수용 가능

- 그럼 vector에서는 특정 index의 element를 얼마나 빠르게 찾을 수 있을까?
 - List는 index의 위치에 따라 성능이 결정(zero는 빠르고, 선형으로 증가하면 느려짐)되지만,
 - Vector는 하나의 index가 32개로 구성되기 때문에 훨씬 빠른 성능를 보여줄 것이다.
 - 예를 들어 1000개의 size를 가진 vector라면 2번의 access(depth)로 조회가 가능하다.
 - vector에서의 depth는 아주 느리게 증가한다.
- 또 다른 vector의 장점은 대량의(bulk) 연산에 좋은 성능을 보여준다. (traverse a sequence와 같은.. )
 - map, foldLeft와 같은 함수들이 처리하는 32개의 vector는 프로세서에서 하나의 cache line에 존재하게 될것이고,  접근속도 역시 상당히 빨라 질 것이다.  
 - 반면에 list는 하나의 element가 나머지 list에 대한 pointer를 가지고 있는 구조라서, 하나의 cache에 함께 존재하는지 장담할 수 없다.


- Lis는 head 또는 tail을 통하여 재귀적인 호출을 할 경우에 효과적이다.
- Vector는 map, fold, filter와 같은 bulk operation에 효과적이다.

## Operations on Vectors
- List와 유사하게 생성한다.
```
val nums = Vector(1, 2, 3, -88)
val people = Vector("Bob", "James", "Peter")
```
- ::만 제외하고, List와 동일한 연산자를 지원한다.
- x :: xs 대신에 아래와 같은 연산자 지원
 - x +: xs : x를 xs 앞에 추가한다.
 - xs :+ x : x를 xs 뒤에 추가한다.
 ```
 5 +: nums // Vector(5, 1, 2, 3, -88)
 nums :+ 5 // Vector(1, 2, 3, -88, 5)
 ```

## Class Hierarchy
List와 Vector의 기본 클래스는 Seq. Seq는 Iterable의 sub class이다.

## Arrays and Strings
```
val xs = Array(1, 2, 4, 44)
val s1 = xs map(x => x * 2)
xs.foreach(println)

val s = "Hello World"
s filter (c => c.isUpper)
```

## Ranges
- 또 다른 간단한 sequence는 range다.
- 3개의 연산자를 가지고 있으며,
 - to (inclusive), until (exclusive), by (다음 단계를 결정하는 값을 지정)
```
val r: Range = 1 until 5 // Range(1, 2, 3, 4)
val s: Range = 1 to 5    // Range(1, 2, 3, 4, 5)
1 to 10 by 3             // Range(1, 4, 7, 10)
6 to 1 by -2             // Range(6, 4, 2)
```

## Some more Sequence Operations
- xs exists p : p(x)가 하나라도 true이면 true
- xs forall p : p(x)가 모두 true를 반환하면 true
- xs zip ys :

```
val x1 = Array(1,2)
val x2 = Array(10, 11)
val str = "Hello World"

x1 exists (_ > 1)  // true
x1 exists (_ > 2)  // false

x1 forall (_ > 0) // true
x1 forall (_ > 1) // false

val x3 = x1 zip x2
x3.foreach(println)  // (1,10), (2, 11)

val x4 = x3.unzip
x4._1.foreach(println) // (1,2)

str flatMap (c => List('.', c)) //.H.e.l.l.o. .W.o.r.l.d

x1.sum  // 3
x1.product // 2
x1.max  // 2
x1.min  // 1
```


## Example : Combinations
x(1 ~ M)와 y(1 ~ N)의 모든 combinationd을 list로 변환
```
(1 to 5) flatMap (x => (1 to 2) map (y => (x, y)))
//  Vector((1,1), (1,2), (2,1), (2,2), (3,1), (3,2), (4,1), (4,2), (5,1), (5,2))
```

## Example : Scala product
2개 Vector의 scala product를 계산하라.

- n(1 ~ n), sum(x1*y1 + x2*y2 + ..... xn*yn)

```
def scalaProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
  (xs zip ys).map(xy => xy._1 * xy._2).sum
}
scalaProduct(Vector(1, 2, 3), Vector(1,2,3))

// res0: Double = 14.0
```
위의 코드를 patten matching을 이용해서 구현해 보자
```
def scalaProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
  (xs zip ys).map{ case (x, y) => x * y}.sum
}
scalaProduct(Vector(1, 2, 3), Vector(1,2,3))
```

## Excercise : Prime
```
def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d != 0)
isPrime(3) // true
isPrime(4) // false
```


# 6.2  Combinatorial Search and For-Expressions
## Handling Nested Sequences
- 양수 n이 주어졌을때, 1 <= j < i < n을 만족하고, i + j가 prime number인 모든 i와 j를 찾아라.
- imperative 프로그래밍에서는 이중 for loop를 이용하여 구현하게 될 것이다.
- Functional 방식으로는 어떻게 구현하는지 보자.
 - 우선 (i, j) pair를 생성한다.
 - i + j가 prime인 pair를 Filtering한다.
- 아래 코드처럼 구현될 수 있다.
```
(1 until 5 ) flatMap (i => (1 until i ) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))
//Vector((2,1), (3,2), (4,1), (4,3))
```

## For-Expression Example
for (s) yield e 스타일로 호출하는 것을 For-Expression이라 한다.ㄴ
```
case class Person(name: String, age: Int)

for( p <- persons if p.age > 20) yeild p.name
2개의 구문은 동일한 결과를 리턴한다.
persons filter (p => p.age > 20) map (p => p.name)
```
위의 scalaProduct 함수를 For-Expression방식으로 구현해 보자
```
def scalaProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
  //(xs zip ys).map{ case (x, y) => x * y}.sum
  (for (
    (x, y) <- xs zip ys
  ) yield x * y).sum
}
scalaProduct(Vector(1, 2, 3), Vector(1,2,3))
```


# 6.3 Combinatorial_Search_Example

## Sets
Set은 sequence와 비슷하게 작성할 수 있다.

데부분의 sequence operations은 sets에서도 사용가능하다.
```
val s = (1 to 6).toSet
val fruit = Set("apple", "banana", "pear")
s map (_ + 2)
fruit filter (_.startsWith == "app")
s.nonEmpty
```
