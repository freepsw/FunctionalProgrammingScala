package week2

/**
 * 2. Purely Functional Sets.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean // Int를 함수로 받아서 Boolean을 반환하는 type aliasf를 Set으로 선언함.

  /**
   * Indicates whether a set contains a given element.
   */
  // Int를 받아서 Boolean을 리턴하는 함수에 elem 파라미터로 넘기고, 그 결과를 boolean으로 받음
  // s 함수에 따라서 s(elem)의 결과가 true or false로 결정됨.
  def contains(s: Set, elem: Int): Boolean = s(elem)


  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): Set = (x => x == elem)

  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: Set, t: Set): Set = (x => contains(s,x) || contains(t,x))
  // 아래 방법도 가능함.
  // def union(s: Set, t: Set): Set = (x => s(x) || t(x))
    
  

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` or `t`.
   */
  def intersect(s: Set, t: Set): Set = (x => contains(s,x) && contains(t,x))

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = (x => contains(s,x) && !contains(t,x))

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, p: Int => Boolean): Set = (x => contains(s,x) && p(x))

  /**
   * The bounds for `` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (contains(s,a) && !p(a)) false
      else if(a > 1000) true
      else iter(a + 1)
    }
    iter(-1000)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = !forall(s, x => !p(x))

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: Set, f: Int => Int): Set = (y => exists(s, x => y == f(x)))

  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
