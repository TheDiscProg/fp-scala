package mylist.oo

/**
 * Experiment to check that methods works.
 */
object OOListExperiment1Operations {
  @main
  def testOO():Unit = {
    var numbers = new OOMyList[Int]
      .addHead(10)
      .addHead(5)
      .addTail(20)
      .addTail(15)
    println(s"${numbers.toScalaList}")
    assert(numbers.toScalaList == List(5, 10, 20, 15))

    numbers = numbers.delete(_ % 2 == 0)
    assert(numbers.toScalaList == List(5,15))
    println(s"${numbers.toScalaList}")

    numbers.addTail(3)
    numbers.sort(Ordering[Int])
    assert(numbers.toScalaList == List(3,5,15))
    println(s"${numbers.toScalaList}")

    assert(numbers.size == 3)
    assert(!numbers.isEmpty)
  }
}

/*
  time slice
  CPU multiple cores
 */