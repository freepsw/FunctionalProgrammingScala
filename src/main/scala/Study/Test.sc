import java.io.Serializable

trait Expr {
  def eval: Int = this match {
    case Number(value) => {
      println(value); value
    }
    case Sum(left, right) => left.eval + right.eval
  }
}

case class Number(value: Int) extends Expr {}

case class Sum(left: Expr, right: Expr) extends Expr {}

Sum(Number(3), Number(4)).eval


val sentence = List("The", "best", "things", "in", "life", "are", "free")

sentence match {
  case "The" :: xs => s"Sentence starts with 'The', rest is $xs"
  case first :: second :: _ => s"First word : $first, Second word : $second"
}
/*
pipelines  = pipelines :: A 이건 안되고   오후 02:55
반대는 되요
*/
val list: List[List[String]] = List(List("a", "b"), List("c"), List("d", "e"))
list

var root: List[String] = List[String]()
var leafs: List[String] = List[String]()
root = root ::: List("r1", "r2")
leafs = leafs ::: List("leaf1", "leaf2")

//var pipelines = List[List[String]]()
var pipelines: List[List[String]] = List(List("a", "b"), List("c"), List("d", "e"))

var test: List[String] = List("1", "2")

pipelines = test :: pipelines
//아래와 동일
pipelines = pipelines.::(test)
// 둘다 List[List[String]] 구조에 List[String]을 add하는 방식으로 진행
// 그런데 왜 순서가 서로 다를까?
// scala에서는 left -> right로 해석되므로, test를 pipelines에 추가한다는 의미로 표현했을까?

pipelines = pipelines :: test
// 아래와 동일
pipelines = test.::(pipelines)
// 이 방식은 List[String] 구조에 String을 추가한다는 의미임.
// test Type이 List[String]으로 정해져 있으므로, String이 추가될 것으로 기대하고 있음.
// 하지만 실제 입력되는 값은 List[List[String]]이 입력되어서 에러가 발생함.





//pipelines = pipelines :: test

//pipelines = pipelines :: (root ::: List("test"))

//pipelines = (root ::: List("test")) :: pipelines

//pipelines = pipelines.


//var test: List[String] = test :: (root ::: List("test"))

//pipelines = pipelines :: (root ::: List("test"))
//pipelines

/*


val p2 = list :: pipelines
val p3 = pipelines :: list

var pipelines_1 = List[List[String]]()
val p22 = list :: pipelines_1
val p33 = pipelines_1 :: list

pipelines_1 = list ::: pipelines_1
*/



//pipelines = pipelines :: list
//
//p2


/*
def makePipelines(): List[List[String]] = {
  for (leaf <- leafs) {
    for (edge <- edges) {
      if (edge.target == leaf) {
        pipelines = (root ::: traversal(edge) ::: List(leaf)) :: pipelines scheduledList = List[String]()
      }
    }
  } pipelines
}
*/
