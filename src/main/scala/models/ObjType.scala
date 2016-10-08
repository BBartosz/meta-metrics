package models

trait ObjType{
  val name: String
  val color: String
}

case object ClassObj  extends ObjType{
  val name = "class"
  val color = "#B19CD9"
}
case object TraitObj  extends ObjType {
  val name = "trait"
  val color = "#E8AB84"
}
case object ObjectObj extends ObjType {
  val name = "object"
  val color = "#FDFD96"
}
