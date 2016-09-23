package week2

import scala.math._

/**
  * Created by P088466 on 2016-07-03.
  */

// 자세한 함수 실행을 보려면 ==> scalac -Xprint:typer Week2Test.scala
object Week2Test {
  def main(args: Array[String]): Unit = {

    def m() = 5 * 5
    def f(x: Int): Int = x

    def g(x: => Int): Int = x

    def h(x: () => Int): Int = {
      x()
      // x    Error: type mismatch
    }

    def k(x: Any): Any = x

    def n = 5 * 5

    def run = {
      f(n)
      g(n)
      // h(n)  Error: type mismatch
      h(n _)
      k(n)

      f(m)
      g(m)
      h(m)
      h(m _)
      k(m)
    }

    // onother test
    def t(x: Int) = x

    def a: Int => Int = t
    val b: Int => Int = t



    val tolerance = 0.0001
    def isCloseEnough(x: Double, y: Double) =
      abs((x - y) / x) / x < tolerance

    def fixedPoint(f: Double => Double)(firstGuess: Double) = {
      def iterate(guess: Double): Double = {
        println("interate")
        val next = f(guess)
        println(s"guess:next($guess : $next)")
        if (isCloseEnough(guess, next)) next
        else iterate(next)
      }
      iterate(firstGuess)
    }
    fixedPoint(x => 1 + x / 2)(1)

    def averageDamp(f: Double => Double)(x: Double) = {
      //println(s"x : $x , val : ${(x + f(x))/2}, f(x): ${f(x)}")
      //    println(s"x : $x")
      //    println(s"val : ${(x + f(x))/2}")
      //    println(s"f(x): ${f(x)}")
      (x + f(x)) / 2
    }

    // 왜 y에 1이 할당되었나?
    // 위의 로그를 보면, 1
    def sqrt(x: Double) =
      fixedPoint(averageDamp(y => {
        println(s"y: $y"); x / y
      }))(1)

    sqrt(2)

  }
}
