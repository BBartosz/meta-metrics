trait Machine
trait Car extends Machine

trait Phone{
  def makeCall = "calling"
  def writeMsg = "msg"
  def charge   = "charge"

}
case class Nokia() extends Phone{
  val contacts = List()
  val dateOfProduction = 2005
  def takePhoto = "photo"
}

trait Smartphone extends Phone {
  def openBrowser = "opening"
}
case class Iphone() extends Smartphone {
  def removeJack = {
    "removing"
  }
}
object Samsung extends Smartphone
