import com.sun.imageio.spi.RAFImageOutputStreamSpi

val x = new Rational(1, 2)
val y = new Rational(5, 7)
//x.add(y)
val z = new Rational(3, 2)
x.sub(y).sub(z)
y.add(y)
x.less(y)
x.max(y)

x < y
x max y
x - y - z
//val strange = new Rational(1, 0)
//strange.add(strange) //eror 발생
new Rational(2)


class Rational(x: Int, y: Int) {
  require(y != 0, "denominator must be nonzero")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = {
    if(b == 0) a else gcd(b, a % b)
  }
  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  def less(that: Rational) =
    numer * that.denom < that.numer * denom

  def < (that: Rational) =
    numer * that.denom < that.numer * denom

  def max(that: Rational) =
      if(this < that) that else this
//    if(this.less(that)) that else this

  def add(that: Rational) =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  def neg: Rational = new Rational(-numer, denom)
  def sub(that: Rational) = add(that.neg)

  def + (that: Rational) =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def - (that: Rational) = this + -that
  // unary_를 사용하게 되면 ":" 전에 1칸을 띄워야 한다.
  // 그렇지 않으면 ":" 문자열도 operator nane으로 인식함.
  def unary_- : Rational = new Rational(-numer, denom)




  override def toString = numer + "/" + denom
}