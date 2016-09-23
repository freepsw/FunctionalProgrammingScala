val answer: Array[Int] = Array(3, 5, 8)
def baseball(guess: Array[Int]) ={
  var strike: Int = 0
  var ball: Int = 0
  var seq = 0

  for( a <- guess) {
    if (answer.contains(a)) {
      if(answer(seq).equals(a)) strike += 1
      else ball += 1
    }
    seq += 1
  }
  s"$strike Strke and $ball Ball"
}

val guess: Array[Int] = Array(3,8,5)
baseball(guess)


def swap(p: Tuple2[Int, Int]) = p match{
  case (a: Int, b: Int) => (b, a)
}
val x2 = (1,2)
val y2 = swap(x2)

class BankAccount(initialBalance: Double) {
  private var balance = initialBalance
  def deposit(amount: Double) = { balance += amount; balance}
  def withdraw(amount: Double) = { balance -= amount; balance}
}

class Checkedccount(initialBalance: Double) extends BankAccount(initialBalance) {
  private var balance = initialBalance
  override def deposit(amount: Double) = {
    super.deposit(amount - 1000)
  }
  override def withdraw(amount: Double) = {
    super.withdraw(amount - 1000)
  }
}

val cb = new Checkedccount(1000)
val tmp = cb.deposit(100)
tmp


val a1 = Array("hello", "scala", "world")
val b1 = Array(5, 5, 5)

val c1 = a1.corresponds(b1)(_.length == _)

def test(fun: Int => Int) ={
  x: Int => fun(x)
}

def adjustToPair(fun: (Int, Int) => Int) = {
  x: (Int, Int) => fun(x._1, x._2)
}

val x1 = adjustToPair(_ * _)((6,7))
val pairs = (1 to 10) zip (11 to 20)

val y = pairs.map(adjustToPair(_ * _))


//val arr = new Array[Int](10).map(x => util.Random.nextInt(10))
var arr = Array(1,2,3,4)

def factorial (arr: Array[Int]) = {
  val r = arr.foldLeft(1)(_ * _)
  r
}
println(factorial(arr))


val maxReduce = arr.reduceLeft((a, b) => if (a > b)  a else b)
println(s"max = $maxReduce")


def unless (condition: => Boolean)(block: => Unit)  {
  if(!condition) { block }
}

unless( 0 > 1) { println("unless111") }


def largest(fun: (Int) => Int, inputs: Seq[Int]) = {
  val max = inputs.map(x => fun(x)).max
  max
}
println(largest(x => 10*x - x*x, 1 to 10))

object Twice {
  def apply(x: Int): Int = x * 2
  def unapply(z: Int): Option[Int] = if (z%2 == 0) Some(z/2) else None
}

val x = Twice(22)
x match { case Twice(n) => Console.println(n) } // prints 21

def values(a: (Int) => Int, low: Int, high: Int) = {
  println(s"low = $low, high = $high")
  (low to high) map {x => (x, a(x))}
}

val l1 = values(x => x*x, -5, 5)


val ar = Array(1, 2, 3, 4, 5)
ar.length
val ar11 = Array[Int](5)
ar11(0)


def switch(ar: Array[Int]): Array[Int] = {
  var ar1: Array[Int] = Array[Int]()
  var temp: Int = 0
  var se1: Int = 0
  ar.foreach(i => {
    if(i % 2 == 0 ) {
      ar(se1 -1) = i
      ar(se1) = temp
      println(s"se1 = $se1 i = $i, temp = $temp ar is = ${ar.foreach(print(_))}")
    }
    else temp = i

    se1 += 1
  })
  ar
}

val ar2 = switch(ar)
ar2.foreach(print(_))



case class Person(name: String, age:Int, dept: String)

val p1 = Person("Jack", 23, "S")
val p2 = Person("Park", 36, "K")

def matchDept(p: Person): Unit = p match {
  case Person("Jack", _, _) => println("Jack is here")
}

matchDept(p1)


val a = 10

val b = List(1, 7, 2, 9).reduceLeft(_-_)

def matchFunction(input: Any): Any = input match {
  case 100 => "hundred"
  case "hundred" => 100
  case a:Int =>"100 이 아닌 Int " + a
  case _ => "기타"
}

matchFunction(9)