package json

import models.{NodeStats, TreeBranch, TreeData}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Writes}

trait JsonMarshallers {
  implicit val treeBranchWrites: Writes[TreeBranch] = (
    (JsPath \ "source").write[String] and
      (JsPath \ "target").writeNullable[String] and
      (JsPath \ "type").write[String]
    )(unlift(TreeBranch.unapply))
  implicit val treeNodeStatsWrite = Json.writes[NodeStats]
  implicit val treeDataWrite = Json.writes[TreeData]
}
