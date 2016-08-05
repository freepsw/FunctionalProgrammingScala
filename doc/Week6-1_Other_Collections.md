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
``
