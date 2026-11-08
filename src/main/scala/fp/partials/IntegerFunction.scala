package fp.partials

class IntegerFunction extends IntegerFunctionAlgebra {

  def divideBy(x: Int, y: Int): Int = x / y

//  def partialDivide(x: Int, y: Int) =
//    case (x, y) if y != 0 => x/y
//    case - => 8


  def partialDivideBy: PartialFunction[(Int, Int), Int] =
    case (x, y) if y != 0 => x/y

  val divide: PartialFunction[(Int, Int), Int] =
    case (x, y) if y != 0 => x / y

  val safeDivide: ((Int, Int)) => Option[Int] =
    divide.lift

  def divideByOption(x: Int, y: Int): Option[Int] =
    Option.when(y != 0)(divideBy(x, y))
}
