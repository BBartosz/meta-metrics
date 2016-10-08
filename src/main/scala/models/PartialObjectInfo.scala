package models

case class PartialObjectInfo(className: String, parents: Seq[String])
case class FullObjectInfo(className: String, parents: Seq[String], children: Seq[PartialObjectInfo])


case class TreeBranch(source: String, target: Option[String], tpe: String = "suit")
case class NodeStats(typeOfObject: String,
                     numberOfMethods: Int,
                     numberOfVals: Int,
                     numberOfVars: Int,
                     numberOfAbstractMethods: Int,
                     color: String){
  def updateNumberOfMethods = this.copy(numberOfMethods = numberOfMethods + 1)
  def updateNumberOfVals = this.copy(numberOfVals = numberOfVals + 1)
  def updateNumberOfVars = this.copy(numberOfVars = numberOfVars + 1)
}
object NodeStats{
  def initial(typeOfObject: ObjType) = NodeStats(typeOfObject.name, 0, 0, 0, 0, typeOfObject.color)
}
