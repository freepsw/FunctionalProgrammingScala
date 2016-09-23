package Study

/**
  * Created by P088466 on 2016-08-04.
  */
object Test {
  def main (args: Array[String]): Unit = {
    val fruit1 = "apples" :: ("oranges" :: ("pears" :: Nil))
    val fruit2 = "mangoes" :: ("banana" :: Nil)

    // use two or more lists with ::: operator
    var fruit = fruit1 ::: fruit2
    println( "fruit1 ::: fruit2 : " + fruit )

    // use two lists with Set.:::() method
    fruit = fruit1.:::(fruit2)
    println( "fruit1.:::(fruit2) : " + fruit )

    // use two lists with Set.:::() method
    fruit = fruit2.:::(fruit1)
    println( "fruit2.:::(fruit1) : " + fruit )

    // pass two or more lists as arguments
    fruit = List.concat(fruit1, fruit2)
    println( "List.concat(fruit1, fruit2) : " + fruit  )
  }
}
