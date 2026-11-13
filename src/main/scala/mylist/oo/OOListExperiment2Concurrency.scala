package mylist.oo

import java.time.{Duration, Instant}

object OOListExperiment2Concurrency {

  @main
  def concurrentTest():Unit = {
    val numbers = new OOMyList[Int]

    val threads = (1 to 10).map{threadNumber =>
      new Thread(() =>
        val thread = Thread.currentThread()
        println(s"Thread Started: ${thread.threadId()}/${thread.getName}")
        (1 to 10000).foreach{ i =>
          numbers.addTail(threadNumber * 10000 + i)
        }
      )
    }
    val startTime = Instant.now()

    threads.foreach(_.start())
    threads.foreach(_.join(10000))
    val endTime = Instant.now()
    val duration = Duration.between(startTime, endTime)
    println(s"Time taken: ${duration.toMillis}")
    println(s"Size of list: ${numbers.size}")
    assert(numbers.size == 100000)
  }
}
