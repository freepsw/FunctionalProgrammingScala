def encode[T](xs: List[T]): List[(T, Int)] = {
  pack(xs) map (ys => (ys.head, ys.length))
}

encode(List("a", "a", "a", "b", "c", "c", "a"))
//List(("a", 3), ("b", 1), ("c", 2), ("a", 1))


def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (first, rest) = xs span (y => y == x)
    first :: pack(rest)

}
pack(List("a", "a", "a", "b", "c", "c", "a"))
//List(List("a", "a", "a"), List("b"), List("c", "c"), List("a"))


val x = List(15, 10, 5, 8, 20, 12)
val y0 = x.groupBy(_ > 10)
val y1 = x.partition(_ > 10)
val y2 = x.span(_ > 10)
val y3 = x.splitAt(2)


List(1, 2, 3, -4, 5) takeWhile (_ > 0)
List(1, 2, 3, -4, 5) filter (_ > 0)

List(1, 2, 3, -4, 5) dropWhile (_ > 0)

def posElems1(xs: List[Int]): List[Int] = xs match {
  case Nil => xs
  case y :: ys => if(y > 0) y :: posElems1(ys) else posElems1(ys)
}

def posElems(xs: List[Int]): List[Int] = {
  xs filter (x => x > 0)
}


def scaleList(xs: List[Double]): List[Double] = xs match {
  case Nil => Nil
  case y :: ys => y * y :: scaleList(ys)
}

def scaleList(xs: List[Double], factor: Double): List[Double] = {
  xs map(x => x * x)
}

/*
def scaleList(xs: List[Double], factor: Double): List[Double] = {
  xs map(x => x * factor)
}
*/


/*
def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
  case Nil => xs
  case y :: ys => y * factor :: scaleList(ys, factor)
}
*/

