package week4

/**
  * Created by P088466 on 2016-08-03.
  */
class Calc {
  var sum = 0
  def add(b: Byte): Unit = {
    sum += b
  }
}

object ExCalc {
  def main(args: Array[String]) : Unit = {
    println(s"Unit = $Unit")
    var testAc = Unit
    println(s"test = $testAc")
    var c = new Calc
    var result = c.add(10)
    println(s"result = $result")
  }
}