object Decompotsition {

  trait Expr {
    def isNumber: Boolean
    def isSum: Boolean
    def numValue: Int
    def leftOp: Expr
    def rightOp: Expr
  }

  class Number(n: Int) extends Expr {
    def isNumber: Boolean = true
    def isSum: Boolean = false
    def numValue: Int = n
    def leftOp: Expr = throw new Error("Number.leftOp")
    def rightOp: Expr = throw new Error("Number.rightOp")
  }

  class Sum(e1: Expr, e2: Expr) extends Expr {
    def isNumber: Boolean = false
    def isSum: Boolean = true
    def numValue: Int = throw new Error("Sum.numVal")
    def leftOp: Expr = e1
    def rightOp: Expr = e2
  }

/*
  class Prod(e1: Expr, e2: Expr) extends Expr
  class Var(x: String) extends Expr
   */


  def eval1(e: Expr): Int = {
    if(e.isNumber) e.numValue
    else if (e.isSum) eval1(e.leftOp) + eval1(e.rightOp)
    else throw new Error("Unknown expression " + e)
  }

  def eval(e: Expr): Int =
    if (e.isInstanceOf[Number])
      e.asInstanceOf[Number].numValue
    else if (e.isInstanceOf[Sum])
      eval(e.asInstanceOf[Sum].leftOp) + eval(e.asInstanceOf[Sum].rightOp)
    else throw new Error("Unknown expression " + e)
}

object OODecomposition{
  trait Expr{
    def eval: Int
  }

  class Number(n: Int) extends Expr {
    def eval: Int = n
  }

  class Sum(e1: Expr, e2: Expr) extends Expr{
    def eval: Int = e1.eval + e2.eval
  }
}