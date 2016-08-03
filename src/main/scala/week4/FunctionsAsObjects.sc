import java.util.NoSuchElementException

/*
Function(A => B)을 object처럼 활용하기 위해서
scala.Function1[A, B]와 같은 class를 미리 생성해 놓음
Function22까지 존
*/
trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty: Boolean= true
  def head: Nothing = throw new NoSuchElementException("Nil. head")
  def tail: Nothing = throw new NoSuchElementException("Nil. tail")
}

object List {
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, new Nil))
  def apply[T]() = new Nil
}

(x: Int) => x*x
List()


class Calc {
  var sum = 0
  def add(b: Byte): Unit = {
    sum += b
  }
}

object ExCalc {
  def main(args: Array[String]) : Unit = {
    var c = new Calc

  }
}