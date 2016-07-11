"# FunctionalProgrammingScala" 


week3
```scala
object Test {
  val t1 = new NonEmptySet(3, new EmptySet, new EmptySet)
  val t2 = t1 incl 4
  val s1 = t2 incl 2
  val s2 = s1 incl 7
  val t3 = new NonEmptySet(2, new EmptySet, new EmptySet)
  val t4 = t2 union t3
  val t5 = new NonEmptySet(7, new EmptySet, new EmptySet)
  val t6 = t5 union t4

abstract class IntSet {
  def contains(x: Int): Boolean
  def incl(x: Int): IntSet
  def union(other: IntSet): IntSet
}

class EmptySet extends IntSet {
  override def toString = "."
  def contains(x: Int) = false
  def incl(x: Int) = new NonEmptySet(x, new EmptySet, new EmptySet)
  def union(other: IntSet): IntSet = {println(s"empty : $other"); println("---------------------"); other}
}

class NonEmptySet(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  override def toString =
    "{" + left +  elem  + right + "}"

  def contains(x: Int) = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  def incl(x: Int) = {
    if (x < elem) new NonEmptySet(elem, left incl x, right)
    else if (x > elem) new NonEmptySet(elem, left, right incl x)
    else this
  }

  def union(other: IntSet): IntSet ={
    /*println(s"[$elem] union left : $left + right : $right")
    println(s" left union right = ${(left union right)}")
    println(s" (left union right) union other = ${(left union right) union other}")
    */
    ((left union right) union other) incl elem
  }
}

}
```
