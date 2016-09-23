import math.abs

object exercise {
  val tolerance = 0.0001
  def isCloseEnough(x: Double, y: Double) =
    abs((x - y) / x) / x < tolerance

  def fixedPoint(f: Double => Double)(firstGuess: Double) = {
    def iterate(guess: Double): Double = {
      //println("interate")
      val next = f(guess)
      println(s"guess:next($guess : $next)")
      if (isCloseEnough(guess, next)) next
      else iterate(next)
    }
    iterate(firstGuess)
  }
  fixedPoint(x => 1 + x/2)(1)

  def averageDamp(f: Double => Double)(x: Double) = {
    println(s"(x + f(x))/2 : $x , f(x): ${f(x)} , val : ${(x + f(x))/2} ")
//    println(s"x : $x")
//    println(s"val : ${(x + f(x))/2}")
//    println(s"f(x): ${f(x)}")
    (x + f(x))/2
  }

  // 왜 y에 1이 할당되었나?
  // 실제 averagePoint함수가 실행되는 시점 = iterate에서 f(guess)를 실행하는 시점임
  // guess는 firstGuess로 "1"과 동일함.
  // 그럼 f(1) => averageDamp(f)(x) 로 연결됨. (f는 fixedPoint에서 지정)
  // averageDamp에서 (x  + f(x))/2 를 다시 호출 (x값은 넘겨받은 1임)
  // 최종 function인 (y => x/y)에서는 x=2, y는 f(1)에서 넘겨진 값인 y=1임.
  // 따라서 x/y = 2/1 (x는 sqrt에서 지정한 값이므로, 변경되지 않음.
  def sqrt(x: Double) =
    fixedPoint(averageDamp(y => {println(s"x:y: $x:$y"); x / y}))(1)

  sqrt(2)


  type Set = Int => Boolean

  def singletonSet(elem: Int): Set = (x => x == elem)
  def contains(s: Set, elem: Int): Boolean = s(elem)
  val s1 = singletonSet(1)
  s1(2)


}