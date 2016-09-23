val capitalOfCountry1 = Map("US" -> "Washington", "Switzerland" -> "Bern")

val cap1 = capitalOfCountry1 withDefaultValue "<unknown>"
cap1("Addorra")

class Poly(val terms0: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = terms0 withDefaultValue(0.0)

  def + (other: Poly) = new Poly((other.terms foldLeft terms)(addTerm))
  def addTerm(terms: Map[Int, Double], term: (Int, Double)) : Map[Int, Double] = {
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))
  }
  override def toString =
    (for((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
}
val p1 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
p1 + p2
/*

class Poly(val terms0: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = terms0 withDefaultValue(0.0)

  def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))
  def adjust(term: (Int, Double)) : (Int, Double) = {
    val (exp, coeff) = term
    exp -> (coeff + terms(exp))
  }
  override def toString =
    (for((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
}
*/



p1.terms(7)


val m1 = Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
val m2 = Map(0 -> 3.0, 3 -> 7.0)
m1 ++ m2




/*
class Poly(val terms: Map[Int, Double]) {
  def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))
  def adjust(term: (Int, Double)) : (Int, Double) = {
    val (exp, coeff) = term
    terms get exp match {
      case Some(coeff1) => exp -> (coeff + coeff1)
      case None => exp -> coeff
    }
  }
  override def toString =
    (for((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
}
*/
val fruit = List("apple", "pear", "orange", "pineapple")
fruit sortWith (_.length < _.length)
fruit.sorted

fruit groupBy (_.head)
fruit.groupBy(_.head)


val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")

capitalOfCountry("US")
romanNumerals("V")
//capitalOfCountry("KOREA")
capitalOfCountry get "KOREA"
capitalOfCountry get "US"


def showCapital(country: String) = capitalOfCountry.get(country) match {
  case Some(capital) => capital
  case None => "missing data"
}

showCapital("US")
showCapital("KOREA")
