package week6

/**
  * Created by P088466 on 2016-08-09.
  */
object Queens {
  def queens(n: Int) : Set[List[Int]] = {
    def placeQueens(k: Int): Set[List[Int]] = {
      println(s"n = $k")
      if(k == 0) Set(List())
      else
        for {
          // 중요!!!
          // 여기서 연속된 <-는 for loop를 의미함. 즉 loop의 연속(여기서는 2중 loop를 나타냄.
          // [STEP 1] k-1 = 0이 되면, 재귀의 계산을 시작
          // List()가 리턴되고, col 0 ~ 3까지 looping을 하면서 isSafe(0, List()) ..... (isSafe(3, List())를 호출한다.
          // queens1 이 List()인 경우는 isSafe가 모두 true. (List()이면 계산할 값이 없는데 true를 리턴.. 아마도 default가 true?
          // queens1의 갯수가 1개 이므로 4번의 loop만 수행
          // yield로 Set(List(0),..., List(3))을 리턴

          // [STEP 2] k-1 = 1이면
          // 위에서 계산한 결과에 따라 queens1이 4개 이므로,
          // queens1(4개) * col (4개) 총 16개의 경우에 수를 점검하여 col :: queens를 수행함.

          // [STEP 3] 이렇게 모든 경우의 수를 다 진행하면, 조건에 만족하는 최종 Set[List[Int]]만 리턴됨.
          queens1 <- placeQueens(k -1)
          col <- 0 until n
          if isSafe(col, queens1)
        //println(s"col = $col queens = $queens isSafe = ${isSafe(col, queens)}")
        } yield {println(s"col = $col - queens1 = $queens1  all = ${col :: queens1}"); col :: queens1 }
    }
    placeQueens(n)
  }

  def isSafe(col: Int, queens: List[Int]): Boolean = {
    //println(s"isSafe($col, ${queens.toString()})")
    val row = queens.length
    val queensWithRow = (row - 1 to 0 by -1) zip queens
    //println(s"qwithR = $queensWithRow")
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
  def main (args: Array[String]) {
    println(s"TEST : ${queens(4)}")
  }
}
