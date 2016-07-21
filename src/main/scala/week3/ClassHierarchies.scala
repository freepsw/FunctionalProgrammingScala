package week3

/**
  * Created by P088466 on 2016-07-19.
  */
object ClassHierarchies {
  def main (args: Array[String]) ={
    // incl Test code
    /*val t1 = new NonEmptySet(3, new EmptySet, new EmptySet)
    val t2 = t1 incl 4*/

    // union Test code
    val m1 = new NonEmptySet(21, new EmptySet, new EmptySet)
    //val m2 = m1 incl 20
    val m2 = m1 incl 19
    val m3 = m2 incl 17
    val m4 = m3 incl 15
    val m5 = m4 incl 27
    val m6 = m5 incl 22

    val n1 = new NonEmptySet(9, new EmptySet, new EmptySet)
    val n2 = n1 incl 10

    println(m6.toString)
    println(n2)

    val u = m6 union n2


    /*
      val s1 = new NonEmptySet(1, new EmptySet, new EmptySet)
      val s2 = new NonEmptySet(2, new EmptySet, new EmptySet)
      val s3 = new NonEmptySet(5, new EmptySet, new EmptySet)
      val s4 = new NonEmptySet(3, new EmptySet, new EmptySet)


      val t1 = new NonEmptySet(3, s2, new EmptySet)
      val t2 = new NonEmptySet(1, new EmptySet, s3)
      val u4 = t1 union t2
    */
    /*
    위의 동작을 구체적으로 설명하면,
      step 00. IntSet 생성
        t1: NonEmptySet = {{.2.}3.}
        t2: NonEmptySet = {.1{.5.}}

      step 01. t1 union t2 시작
        // t1의 root node의 상태
        this = 3 other: {.1{.5.}} left : {.2.} right: .

      step 02. left({.2.}) union right를 호출
        step 02-01. left(NonEmptySet)의 union 호출
        // left와 right가 모두 EmptySet
        // 1. left(Empty) union (Empty) => Empty
          this = 2 other: . left : . right: .
          empty other : .
          ---------------------
          empty other : .
          ---------------------
        // 2. (left union right)=EmptySet union other(EmptySet) => Empty
           (left union right) union other = .
        // 3. Empty incl elem(2) => {.2.} 리턴
           ((left union right) union other) incl elem = {.2.}

      step 03. (left union right) => {.2.} union other({.1{.5.}}) => t2
        // step 02에서 생성된 NonEmptySet의 union을 호출
        // other가 t2로 지정됨
        this = 2 other: {.1{.5.}} left : . right: .

        // 1. left union right (. union .) => EmptySet
        empty other : .
        ---------------------

        //2. . union other => {.1{.5.}}
        (left union right) union other = {.1{.5.}}
        empty other : {.1{.5.}}
        ---------------------

        //3. {.1{.5.}} incl 2
          // incl은 node의 값을 기준으로 적으면 left, 크면 right
          // 지금 예제는 1보다 크고, 5보다 작으므로, 5의 left에 추가됨 )
         ((left union right) union other) incl elem = {.1{{.2.}5.}}

      step 04. step03의 결과({.1{{.2.}5.}})를 현재 node 3와 incl
        // 3이 1보다 크고, 5보다 적고, 2보다 크므로 => {.1{{.2{.3.}}5.}}
       (left union right) union other = {.1{{.2.}5.}}
       ((left union right) union other) incl elem = {.1{{.2{.3.}}5.}}

      step 05. 최종 결과
        u4: IntSet = {.1{{.2{.3.}}5.}}
    */
    // 아래 2개 연산은 동일하다 (1 incl 2 vs 1 union 2)
    // 그런데 결과는 서로 다르다.
    // 1 incl 2는 {.1{.2.}} => 1의 right node에 2를 추가
    // 1 union 2는 {{.1.}2.} => 2의 left node에 1을 추
    // 왜 NonEmptySet의 union은 lefe와 right를 먼저 union 할까?
    /*  val s3 = s1 incl 2
      val u3 = s1 union s2
    */
  }

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
    // other와 비교할 child node가 없으므로, other를 return함
    def union(other: IntSet): IntSet = {
      println(s"empty other : $other")
      // println("---------------------")
      other
    }
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
      //println(s"elem = $elem other: $other left : $left right: $right")
      println(s"[$elem]=in (left union right) union other incl elem = (($left union $right) union $other) incl $elem")
      val left_u_right_other = (left union right) union other
      //    println(s" (left union right) union other = ${left_u_right_other}")
      val incl_elem = left_u_right_other incl elem
      //println(s" ((left union right) union other) incl elem = ${incl_elem}")

      println(s"[$elem]=out (left union right) union other incl elem = (($left union $right) union $other) incl $elem ==> $incl_elem")
      incl_elem
      //((left union right) union other) incl elem
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
