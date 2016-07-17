object scratch{
// incl Test code
/*val t1 = new NonEmptySet(3, new EmptySet, new EmptySet)
val t2 = t1 incl 4*/

// union Test code
val u1 = new NonEmptySet(1, new EmptySet, new EmptySet)
val u2 = new NonEmptySet(2, new EmptySet, new EmptySet)
val u3 =  u1 union u2
/*val s1 = t2 incl 2
val s2 = s1 incl 7
val t3 = new NonEmptySet(2, new EmptySet, new EmptySet)
val t4 = t2 union t3
val t5 = new NonEmptySet(7, new EmptySet, new EmptySet)
val t6 = t5 union t4*/


// 내부 함수에 대한 implementation이 없음
// new를 통해서 abstract class에 대한 instance를 생성할 수 없음.
abstract class IntSet {
  def contains(x: Int): Boolean
  def incl(x: Int): IntSet
  def union(other: IntSet): IntSet
}

// tree의 구성이 parent node - child nodes(right node, left node)로 구성됨.(3개)
// 마지막 node의 경우 자신의 값(elem) 밑으로는 더 이상 데이터가 없음.
// 이때 child nodes를 표현하기 위해서 EmptySet Node를 생성함.
// 또한 Empty는 포함하고 있는 node가 없으므로 contains가 항상 false임.
// incl은 input x를 포함한 EmptySet Nodes를 반환함. (항상 3개 node로 구성)
class EmptySet extends IntSet {
  override def toString = "."
  def contains(x: Int) = false
  def incl(x: Int) = new NonEmptySet(x, new EmptySet, new EmptySet)
  def union(other: IntSet): IntSet = {println(s"empty : $other"); println("---------------------"); other}
}

// 마지막 node를 제외하고는 대부분 데이터가 있음.
// contains에서 input x가 현재 Node의 elem보다 작으면 left Node의 contains를 호출
// x == elem이면 true를 리턴 (현재 찾고 이는 Node임)
// incl은 새로운 추가함..
// week3-4_01.PNG 이미지 참고.

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



/*
 - Scala Class Hierachy picture
 AnyVal 아래의 short, int, long, float간에 점선으로 표시된 것의 의미는
 subType은 아니지만 상호간에 conversion이 가능하다는 의미이다.
 subType으로 표시되려면,
 별도의 변환과정 없이 long -> float으로 변환되어야 하며,
 long->float으로 변환되면서 데이터의 손실도 없어야 한다.

 - Nothing
  abnomal 종료를 반환하는 용도.
   - java " throw Exception"
   - scala는 nothing을 반
  empty collection에 대한 기초 type을 제공
  Set [Nothing]
*/



  def error(msg: String) = throw new Error(msg)
}



