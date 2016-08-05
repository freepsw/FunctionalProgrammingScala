val list = List(1, 2, 3, 4)
list.updated(2,5)

// List의 last 함수 직접 구현
def last[T](xs : List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(ys)
}

def init[T](xs: List[T]): List[T] = xs match{
  case List() => throw new Error("init of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

def concat[T](xs: List[T], ys: List[T]): List[T]= xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}

def reverse[T](xs: List[T]): List[T]= xs match {
  case List() => List()
  case y :: ys =>reverse(ys) ++ List(y)
}

removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)

def removeAt[T](n: Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n +1)


import math.Ordering
def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {  //비교 연산자 전달
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
    merge(msort(fst), msort(snd))
  }
}
val nums = List(2, -4, 5, 7, 1)
msort(nums)

/*

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
*/

/*
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

    // 위의 방식에 비하여 훨씬 가독이 높다. 코드도 줄어들
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
msort(nums)
*/


/*

def msort[T](xs: List[T]): List[T] = {
  val n = xs.length/2

  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if(x < y) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}
*/

/*

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

*/
