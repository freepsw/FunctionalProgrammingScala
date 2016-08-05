
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