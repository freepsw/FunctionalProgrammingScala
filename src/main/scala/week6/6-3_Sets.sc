// 체스보드에 놓은 n개의 queens 위치
// input : row의 갯수 (체스보드)
// output : 답의 갯수 (정답을 Set의 갯수만큼 제공)
def queens(n: Int) : Set[List[Int]] = {
  def placeQueens(k: Int): Set[List[Int]] = {
    println(s"n = $k")
    if(k == 0) Set(List())
    else
      for {
        queens1 <- placeQueens(k -1)
        col <- 0 until n
        if isSafe(col, queens1)
        //println(s"col = $col queens = $queens isSafe = ${isSafe(col, queens)}")
      } yield {println(s"col = $col - queens1 = $queens1  all = ${col :: queens1}"); col :: queens1 }
  }
/*
isSafe(0, List())
qwithR = Vector()
col = 0 - queens1 = List()  all = List(0)
isSafe(1, List())
qwithR = Vector()
col = 1 - queens1 = List()  all = List(1)
isSafe(2, List())
qwithR = Vector()
col = 2 - queens1 = List()  all = List(2)
isSafe(3, List())
qwithR = Vector()
col = 3 - queens1 = List()  all = List(3)
isSafe(0, List(0))
qwithR = Vector((0,0))
isSafe(1, List(0))
qwithR = Vector((0,0))
isSafe(2, List(0))
qwithR = Vector((0,0))
col = 2 - queens1 = List(0)  all = List(2, 0)
isSafe(3, List(0))
qwithR = Vector((0,0))
col = 3 - queens1 = List(0)  all = List(3, 0)
isSafe(0, List(1))
qwithR = Vector((0,1))
isSafe(1, List(1))
qwithR = Vector((0,1))
isSafe(2, List(1))
qwithR = Vector((0,1))
isSafe(3, List(1))
qwithR = Vector((0,1))
col = 3 - queens1 = List(1)  all = List(3, 1)
isSafe(0, List(2))
qwithR = Vector((0,2))
col = 0 - queens1 = List(2)  all = List(0, 2)
isSafe(1, List(2))
  */
  placeQueens(n)
}

def isSafe(col: Int, queens: List[Int]): Boolean = {
  println(s"isSafe($col, ${queens.toString()})")
  val row = queens.length
  val queensWithRow = (row - 1 to 0 by -1) zip queens
  println(s"qwithR = $queensWithRow")
  queensWithRow forall {
    case (r, c) => col !=c && math.abs(col - c) != row - r
  }
}

def show(queens: List[Int]) = {
  println("queen" + queens.reverse.toString())
  val lines =
    for (col <- queens.reverse)
      yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
  "\n" + (lines mkString "\n")
}
println(s"TEST : ${queens(4)}")
//queens(4) map show


val list = List(1, 2, 3, 4)
val r = list.length
val rwith = (r-1 to 0 by -1) zip list

val ss : Set[List[Int]] = Set(List(0), List(1), List(2))

for {
  queens1 <- ss
  col <- 0 to 2
} {println(s"col = $col - queens1 = $queens1  all = ${col :: queens1}"); col :: queens1 }



/*
val test = for {
  col <- 0 until 5
  if( col > 2)
} yield col :: List(0)
*/

/*
val s = (1 to 6).toSet
val fruit = Set("apple", "banana", "pear")
s map (_ + 2)
fruit filter (_.startsWith("app"))
s.nonEmpty


s map (_ / 2 )
s contains 5
*/

List(1,2,3).forall(x => x > 1)