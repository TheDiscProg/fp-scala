package mylist.oo

import mylist.MyList

class OOMyList[A] extends MyList[A] {

  private class Node(var value: A, var next: Option[Node])

  private var head: Option[Node] = None
  private var tail: Option[Node] = None
  private var count = 0L

  // O(1)
  override def addHead(a: A): MyList[A] = {
    val node = new Node(a, head)
    head = Some(node)
    if tail.isEmpty then tail = Some(node)
    count += 1
    this
  }

  // O(1)
  override def addTail(a: A): MyList[A] = {
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

  // O(n^2)
  override def sort(ord: Ordering[A]): MyList[A] = {
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

  // O(n)
  override def delete(p: A => Boolean): MyList[A] =
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

  // O(1)
  override def size: Long = count

  // O(1)
  override def isEmpty: Boolean = count == 0

  override def toScalaList: List[A] = {
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

/*

    prove sin t /cos t == tan t

   If known sin x and cos x, tan x = sin x / cos x, defined for cos x != 0

 */
