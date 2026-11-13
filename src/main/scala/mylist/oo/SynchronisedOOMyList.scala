package mylist.oo

import mylist.MyList

class SynchronisedOOMyList[A] extends MyList[A] {

  private class Node(var value: A, var next: Option[Node])

  private var head: Option[Node] = None
  private var tail: Option[Node] = None
  private var count = 0L

  override def addHead(a: A): MyList[A] = synchronized {
    val node = new Node(a, head)
    head = Some(node)
    if tail.isEmpty then tail = Some(node)
    count += 1
    this
  }

  override def addTail(a: A): MyList[A] = synchronized {
    val node = new Node(a, None)
    tail match {
      case Some(last) =>
        last.next = Some(node)
        tail = Some(node)
      case None =>
        head = Some(node)
        tail = Some(node)
    }
    count += 1
    this
  }

  override def sort(ord: Ordering[A]): MyList[A] = synchronized {
    var swapped = true
    while swapped do {
      swapped = false
      var current = head
      while current.exists(_.next.nonEmpty) do
        val node = current.get
        val next = node.next.get
        if ord.gt(node.value, next.value) then
          val temp = node.value
          node.value = next.value
          next.value = temp
          swapped = true
        current = node.next
    }
    this
  }

  override def delete(p: A => Boolean): MyList[A] = synchronized {
    var current = head
    var previous: Option[Node] = None
    while current.nonEmpty do {
      val node = current.get
      if p(node.value) then {
        previous match
          case Some(prev) =>
            prev.next = node.next
          case None =>
            head = node.next
        if tail.contains(node) then tail = previous
        count -= 1
      } else {
        previous = current
      }
      current = node.next
    }
    this
  }

  override def size: Long = count

  override def isEmpty: Boolean = count == 0

  override def toScalaList: List[A] = synchronized {
    var result  = List.empty[A]
    var current = head
    while current.nonEmpty do {
      val node = current.get
      result = node.value :: result
      current = node.next
    }
    result.reverse
  }
}
