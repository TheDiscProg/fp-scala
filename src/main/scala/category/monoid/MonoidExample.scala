package category.monoid

import cats.Monoid
import cats.kernel.CommutativeMonoid.*
import cats.syntax.all.*

object MonoidExample {
  
  def main():Unit = {
    val aString: String = Monoid[String].combine("hello", "world")
    println(s"Got: $aString.")



    val intList = List(1,2,3)
    val sum = Monoid[Int].combineAll(intList)

    println(s"Sum: $sum")
    println(s"Identity Element for String: [${Monoid[String].empty}]")
    println(s"Identity Element for Int: ${Monoid[Int].empty}")

    val combined = combineAll(intList)
    println(s"$combined")

  }
}
