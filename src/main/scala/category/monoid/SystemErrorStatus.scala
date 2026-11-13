package category.monoid

import cats.kernel.Monoid
import cats.syntax.semigroup.*
import cats.data.{Validated, ValidatedNel}
import cats.syntax.apply.*

case class SystemErrorStatus(
  code:    Int,
  message: String
)

object SystemErrorStatus {
  given Monoid[SystemErrorStatus] with {
    override def empty: SystemErrorStatus = SystemErrorStatus(200, "")

    override def combine(x: SystemErrorStatus, y: SystemErrorStatus): SystemErrorStatus =
      SystemErrorStatus(
        code = math.max(x.code, y.code),
        message = List(x.message, y.message)
          .filter(_.nonEmpty)
          .mkString("; ")
      )
  }
}

object SystemErrorSystemAccumulate {

  def accumulateErrors(): Unit = {
    val e1 = SystemErrorStatus(400, "Invalid email")
    val e2 = SystemErrorStatus(400, "Invalid postcode")
    val e3 = SystemErrorStatus(400, "Invalid date")

    val result = e1 |+| e2 |+| e3
    println(s"Errors: $result")
  }
}

object UsingValidated {

  @main
  def useValidate(): Unit = {
    val result = Customer.validateCustomer(name = "", email = "user", postcode = "", age = 19)
    println(s"Result: $result")
  }
}

final case class Customer(
  name:     String,
  email:    String,
  postcode: String,
  age:      Int
)
object Customer {

  import cats.syntax.validated.*

  def validateCustomer(
    name:     String,
    email:    String,
    postcode: String,
    age:      Int
  ): ValidatedNel[String, Customer] =
    (
      validateName(name),
      validateEmail(email),
      validatePostcode(postcode),
      validateAge(age)
    ).mapN(Customer.apply)

  private def validateName(name: String): ValidatedNel[String, String] =
    if name.nonEmpty then name.validNel
    else "Name is required".invalidNel

  private def validateEmail(email: String): ValidatedNel[String, String] =
    if email.contains("@") then email.validNel
    else "Invalid email address".invalidNel

  private def validatePostcode(postcode: String): ValidatedNel[String, String] =
    if postcode.nonEmpty then postcode.validNel
    else "Postcode is required".invalidNel

  private def validateAge(age: Int): ValidatedNel[String, Int] =
    if age >= 18 then age.validNel
    else "Customer must be at least 18".invalidNel

}
