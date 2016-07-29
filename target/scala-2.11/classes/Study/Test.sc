trait Expr {
  def eval: Int = this match {
    case Number(value) => {println(value); value}
    case Sum(left, right) => left.eval + right.eval
  }
}
case class Number(value: Int) extends Expr { }
case class Sum(left: Expr, right: Expr) extends Expr { }

Sum(Number(3), Number(4)).eval


val sentence = List("The", "best", "things", "in", "life", "are", "free")

sentence match {
  case "The":: xs => s"Sentence starts with 'The', rest is $xs"
  case first :: second :: _ => s"First word : $first, Second word : $second"
}



