package category.monoid

import cats.*
import cats.syntax.all._

object SemigroupKExample {

  @main
  def main(): Unit = {
    val list1 = List(1, 2, 3, 4, 5)
    val list2 = List(6, 7, 8, 9, 10)

    val combinedList = SemigroupK[List].combineK(list1, list2)
    println(s"List: ${combinedList.mkString(",")}")
    val alist = list1 |+| list2
    println(s"Alist: ${alist.mkString(",")}")

    val optionA = Some("Hello")
    val optionB = Some("World!")
    val combinedOption = SemigroupK[Option].combineK(optionA, optionB)
    println(s"${combinedOption.getOrElse("Boom!")}")
  }
}
