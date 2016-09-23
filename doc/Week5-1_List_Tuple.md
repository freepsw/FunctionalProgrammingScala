# 5.1 - More Functions on Lists

## List Methods
list 자료구조는 왼쪽이 head(첫번째 element)이고 오른쪽이 last(마지막 element)이다.
- xs.length
- xs.last  : 가장 마지막 element 리턴
- xs.init : last를 제외한 모든 element 리턴
- xs.tail : head를 제외한 모든 element 리턴
- xs.take n : head에서 n번째 elements까지 리턴
- xs.drop n : head에서 n번째 요소를 제외한 모든 elements 리턴 (except xs.take n)
- xs(n) : n번째 index에 있는 element 리턴 = xs apply n 동일한 결과.

Creating new list :
- xs ++ ys :  ys list 뒤에 xs를 추가한 list를 리턴
- xs.reverse : 순서를 reverse한 list를 리턴
- xs updated (n, x) : list는 immutable 이므로, 값을 변경할 수 없으나, update함수를 이용하여 n번째 index의 값을 x로 변경한 list를 생성한다.
```
val list = List(1, 2, 3, 4)
list.updated(2,5) // => res0: List[Int] = List(1, 2, 5, 4) 생성
```

Finding elements
- xs indexOf x : x와 동일한 값을 가지는 첫번째 element의 index를 리턴. 없으면 -1
- xs contains x : xs indexOf x >=0 와 동일. boolean을 반환


## Implementation of last
haed의 수행시간은 constant time이 소요된다.
last는 어떨까? 확인해 보기 위해서 별도의 last 함수를 구현해 보자
```
def last[T](xs : List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(ys)
}
```
last의 수행단계는 list의 length에 비례하여 증가한다.

## Exercise
init 함수를 별도로 구현해 보자. (last와 유사하게)
```
def init[T](xs: List[T]): List[T] = xs match{
  case List() => throw new Error("init of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}
```

## Implementation of Concatenation
concat함수는 list에서 아래와 같이 호출할 수 있다.
변수의 위치에 따라서 연산자가 달라짐을 주의하자!
```
xs ::: ys
또는
ys.:::(xs)
```
아래와 같이 구현된 concat함수의 복잡도는 xs의 length에 비례한다.
```
def concat[T](xs: List[T], ys: List[T]): List[T]= xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}
```

## Implementation of reverse
reverse함수의 성능은 xs의 length에 비례
```
def reverse[T](xs: List[T]): List[T]= xs match {
  case List() => List()
  case y :: ys =>reverse(ys) ++ List(y)
}
```

## Exercise
list의 n번째 element를 삭제하고, n이 범위를 넘어서는 경우 xs 자체를 리턴하라
```
def removeAt[T](n: Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n +1)
removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)
```
# 5.2 Pairs and Tuples
## Sorting Lits Faster
중요한 예제로, insertion sort보다 효율적인 sort(merge sort)를 설계해 보자.
- 먼저 list가 zero 또는 1개의 element로 구성되었다면, 이미 sorted된 것이다.
- list를 2개의 sub-list로 나눈다(list의 절반 크기)
- 2개의 sub-list를 각각 sort한다.
- sorted된 sub-list를 merge하여 하나의 sorted-list로 생성한다.
```
def msort(xs: List[Int]): List[Int] = {
  val n = xs.length/2

  if (n == 0) xs
  else {
    def merge(xs: List[Int], ys: List[Int]): List[Int] = xs match {
      case Nil => ys
      case x :: xs1 => ys match {
        case Nil => xs
        case y :: ys1 =>
          // (x < y) xs의 head가 ys의 header보다 작으면 x를 앞에 추가하고, 나머지에 대해서 merge 수행.
          // (x > y) xs의 head가 더 크면, ys의 head를 앞에 추가하, 나머지는 merge 수행
          if (x < y) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}
```

## Pairs and Tuples
x와 y 2개로 구성된 pair는 (x, y)로 표현한다.
Example
```
val pair = ("answer", 2) > pair : (String, Int) = (answer, 2)
```
위 pair의 type은 (String, Int)이다.
Tuples은 2개 이상의 elelemts로 구성되어 있으며, pair는 tuples와 비슷하게 동작한다.

## Tuple class
모든 Tuplen class들은 아래와 같이 모델화 되어 있다.
```
case class Tuple2[T1, T2](_1: +T1, _2: +T2) {
  override def toString = "(" + _1 + "," + _2 + ")"
}
```
tuple의 field는 _1, _2 로 접근이 가능하다. ...
그래서
```
val (label, value) = pair 대신에
val label = pair._1
val value = pair._2
```
로 표현이 가능하다.
그러나 일반적으로 pattern matching 방식이 더  선호된다. val (label, value) = pair

그럼 tuple의 pattern matching을 이용하여 merge 함수를 수정해 보자
위의 방식에 비하여 훨씬 가독이 높다. 코드도 줄어들고..
```
def msort(xs: List[Int]): List[Int] = {
  val n = xs.length/2

  if (n == 0) xs
  else {

    def merge_tuple(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if(x < y) x :: merge_tuple(xs1, ys)
        else y :: merge_tuple(xs, ys1)
      }
    }

    val (fst, snd) = xs splitAt n
    merge_tuple(msort(fst), msort(snd))
  }
}

val nums = List(2, -4, 5, 7, 1)
msort(nums)  > res2: List[Int] = List(-4, 1, 2, 5, 7)
```
