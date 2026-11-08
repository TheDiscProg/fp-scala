package io

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random
import cats.syntax.all
import cats.effect.IO
import cats.effect.unsafe.implicits.global

object ProducesDifferentResults {

  def computation: IO[Unit] =
    IO {
      println("hello")
    }

  @main
  def main(): Unit =
    val first:  IO[Unit] = computation
    val second: IO[Unit] = computation
    println("world")
    first.unsafeRunSync()
    second.unsafeRunSync()

}

object CallFunctionOnce {
  given ExecutionContext = ExecutionContext.global

  def computation: Future[Unit] =
    Future {
      println("Hello")
    }

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
