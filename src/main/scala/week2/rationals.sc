object rationals {


  def main(args: Array[String]) {
    val x = new Rational(1, 2)
    x.numer
    x.denom
    x.toString

    val y = new Rational(2, 3)
    x.add(y)
  }
}

class Rational(x: Int, y: Int) {
  def numer = x
  def denom = y
  def addRational(r: Rational, s:Rational): Rational =
    new Rational(
      r.numer * s.denom + s.numer * r.denom,
      r.denom * s.denom
    )

  def add(that: Rational) =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  override def toString = numer + "/" + denom


  def makeString(r: Rational) =
    r.numer + "/" + r.denom

  makeString(addRational(new Rational(1, 2), new Rational(2, 3)))
}






