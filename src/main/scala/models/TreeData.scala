package models

import scala.meta._

case class TreeData(branches: List[TreeBranch], nodeStats: Map[String, NodeStats]){
  def updateTreeData(body: scala.collection.immutable.Seq[scala.meta.Stat],
                     ctorcalls: scala.collection.immutable.Seq[Ctor.Call],
                     tname: Type.Name,
                     objType: ObjType
                    ): TreeData = {
    val newBranches: List[TreeBranch] = buildBranches(ctorcalls, tname)
    val newStats: NodeStats = buildNodeStats(body, tname, objType)

    TreeData(branches ::: newBranches, newNodeStats(tname.toString, newStats))
  }

  private def newNodeStats(tname:String, newStats: NodeStats)  = {
    nodeStats + (tname.toString -> newStats)
  }

  private def buildBranches(parents: scala.collection.immutable.Seq[Ctor.Call], tname: Type.Name): List[TreeBranch] =
    parents.foldLeft(List[TreeBranch]())((acc, elem) => acc :+ TreeBranch(elem.toString, Option(tname.toString)))

  private def buildNodeStats(body: scala.collection.immutable.Seq[scala.meta.Stat], tname: Type.Name, objType: ObjType): NodeStats = {
    body.foldLeft(NodeStats.initial(objType))((acc: NodeStats, elem) => elem match {
      case q"..$mods def $name[..$tparams](...$paramss): $tpe = $expr" => acc.updateNumberOfMethods
      case q"..$mods val ..$patsnel: $tpeopt = $expr" => acc.updateNumberOfVals
      case q"..$mods var ..$patsnel: $tpeopt = $expr" => acc.updateNumberOfVars
      case _ => acc
    })
  }
}
object TreeData {
  val initial = TreeData(Nil, Map())
}
