package futures

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

object ProducesDifferentResults {
  given ExecutionContext = ExecutionContext.global

  def computation: Future[Unit] = Future {
    println("hello")
  }

  def main(): Unit =
    val first: Future[Unit] = computation
    val second = computation
    println("world")
    Thread.sleep(500)

}

object CallFunctionOnce {
  given ExecutionContext = ExecutionContext.global

  def computation: Future[Unit] =
    Future {
      println("Hello")
    }

  @main
  def main(): Unit =
    val shared: Future[Unit] = computation
    val first  = shared
    val second = shared
    println("world")
    Thread.sleep(500)
}

object CallFunctionWithoutFuture {
  def computation: Unit = println("Hello")

  def main(): Unit =
    val first  = computation
    val second = computation
    println("world")
}
