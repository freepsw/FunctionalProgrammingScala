
// 내부 함수에 대한 implementation이 없음
// new를 통해서 abstract class에 대한 instance를 생성할 수 없음.
abstract class InSet {
  def incl(x: Int): InSet
  def contains(x: Int): Boolean
}

// tree의 구성이 parent node - child nodes(right node, left node)로 구성됨.(3개)
// 마지막 node의 경우 자신의 값(elem) 밑으로는 더 이상 데이터가 없음.
// 이때 child nodes를 표현하기 위해서 Empty Node를 생성함.
// 또한 Empty는 포함하고 있는 node가 없으므로 contains가 항상 false임.
// incl은 input x를 포함한 Empty Nodes를 반환함. (항상 3개 node로 구성)
class Empty extends InSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): InSet = new NonEmpty(x, new Empty, new Empty)
}

// 마지막 node를 제외하고는 대부분 데이터가 있음.
// contains에서 input x가 현재 Node의 elem보다 작으면 left Node의 contains를 호출
// x == elem이면 true를 리턴 (현재 찾고 이는 Node임)
// incl은 새로운 추가함..
//
class NonEmpty(elem: Int, left: InSet, right: InSet) extends InSet {
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true

  def incl(x: Int): InSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if(x > elem) new NonEmpty(elem, left, right incl x)
    else this
}

object insets {
  println("Welcome to the scala")
}