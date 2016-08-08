def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  (xs foldRight List[U]())( ??? )

def lengthFun[T](xs: List[T]): Int =
  (xs foldRight 0)( ??? )


def concat[T](xs: List[T], ys: List[T]): List[T] = {
  (xs foldRight ys) ((x, y) => x :: y )
}

4 :: List(1,2,3)
List(1,2,3).::(4)

val t: Int = 4

  //(xs foldLeft ys) ((x, y) => x :: y )
/*

abstract class List[T] {
  def reduceLeft(op: (T, T) => T): T = this.match {
    case Nil => throw new Error("Nil.reduceLeft")
    case x :: xs => (xs foldLeft x) (op)
  }
  def foldLeft[U](z: U)(op: (U, T) => U): U = this match {
    case Nil => z
    case x :: xs => (xs foldLeft op(z, z))(op)
  }
}
*/

def sum(xs: List[Int])     = (xs foldLeft 0)((x, y) => x + y)
def product(xs: List[Int]) = (xs foldLeft 1)(_ * _)

def sum1(xs: List[Int]): Int = xs match {
  case Nil => 0
  case y :: ys => y + sum1(ys)
}