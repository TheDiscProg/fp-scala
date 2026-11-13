package mylist

trait MyList[A] {

  def addHead(a: A):            MyList[A]
  def addTail(a: A):            MyList[A]
  def sort(ord:  Ordering[A]):  MyList[A]
  def delete(p:  A => Boolean): MyList[A]
  def size:                     Long
  def isEmpty:                  Boolean
  def toScalaList:              List[A]
}
