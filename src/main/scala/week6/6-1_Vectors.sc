
def scalaProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
  //(xs zip ys).map{ case (x, y) => x * y}.sum
  (for (
    (x, y) <- xs zip ys
  ) yield x * y).sum
}
scalaProduct(Vector(1, 2, 3), Vector(1,2,3))


for {
  i <- 1 until 5
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)

/*
case class Person(name: String, age: Int)
for( p <- persons if p.age > 20) yeild p.name
persons filter (p => p.age > 20) map (p => p.name)
*/


(1 until 5 ) flatMap (i => (1 until i ) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))
//Vector((2,1), (3,2), (4,1), (4,3))
(1 until 5)
def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d != 0)
isPrime(4)
/*
def scalaProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
  (xs zip ys).map{ case (x, y) => x * y}.sum
}
scalaProduct(Vector(1, 2, 3), Vector(1,2,3))
*/

/*
def scalaProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
  (xs zip ys).map(xy => xy._1 * xy._2).sum
}
scalaProduct(Vector(1, 2, 3), Vector(1,2,3))

Vector(1, 2, 3).zip(Vector(1,2,3))

(1 to 5) flatMap (x => (1 to 2) map (y => (x, y)))
*/


val x1 = Array(1,2)
val x2 = Array(10, 11)
val str = "Hello World"

x1 exists (_ > 1)
x1 forall (_ > 1)

val x3 = x1 zip x2
x3.foreach(println)

val x4 = x3.unzip
x4._1.foreach(println)

str flatMap (c => List('.', c))

x1.sum
x1.product
x1.max
x1.min





val r: Range = 1 until 5
val s: Range = 1 to 5
1 to 10 by 3
6 to 1 by -2



val xs = Array(1, 2, 4, 44)
val s1 = xs map(x => x * 2)
xs.foreach(println)

val h = "Hello World"
h filter (c => c.isUpper)

val nums = Vector(1, 2, 3, -88)
val people = Vector("Bob", "James", "Peter")

5 +: nums
nums :+ 5