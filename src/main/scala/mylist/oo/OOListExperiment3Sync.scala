package mylist.oo

import java.time.{Duration, Instant}

/**
 * Using synchronised libraries that are thread safe
 * Increasing threads while decreasing the workload of each thread
 */
object OOListExperiment3Sync {
  @main
  def syncTest():Unit = {
    val numbers = new SynchronisedOOMyList[Int]

    val startTime = Instant.now()
    val threads = (1 to 1000).map { threadNumber =>
      new Thread(() =>
        val thread = Thread.currentThread()
        println(s"Thread Started: ${thread.threadId()}/${thread.getName}")
        (1 to 100).foreach { i =>
          numbers.addTail(threadNumber * 10000 + i)
        }
      )
    }

    threads.foreach(_.start())
    threads.foreach(_.join(10000))
    val endTime = Instant.now()
    val duration = Duration.between(startTime, endTime)
    println(s"Size of list: ${numbers.size} Time taken: ${duration.toMillis}")
    assert(numbers.size == 100000)
    // ~60 millis
    val sortStart = Instant.now
    numbers.sort(Ordering[Int])
    val endSort = Instant.now
    val sortTime = Duration.between(sortStart, endSort)
    println(s"Time to sort: ${sortTime.toMillis}")
    // ~25 seconds
  }

}
