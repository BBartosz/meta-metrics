import java.io.{File, PrintWriter}

import json.JsonMarshallers
import models.{ClassObj, ObjectObj, TraitObj, TreeData}
import play.api.libs.json.Json

import scala.meta._

object MetricsService extends JsonMarshallers{
  def getMetrics(path: String) = {
    val testFile = new File(path)
    val data: TreeData = testFile.parse[Source] match {
      case Parsed.Success(tree) => tree match {
        case source"..$whateverItIsInFile" => whateverItIsInFile.foldLeft(TreeData.initial)((acc: TreeData, elem) => elem match {
          case q"..$mods trait $tname[..$tparams] extends $template" => template match {
            case template"{ ..$stats } with ..$ctorcalls { $param => ..$body }" => {
              acc.updateTreeData(body, ctorcalls, tname, TraitObj)
            }
          }
          case q"..$mods class $tname[..$tparams] ..$mods2 (...$paramss) extends $template" => template match {
            case template"{ ..$stats } with ..$ctorcalls { $param => ..$body }" => {
              acc.updateTreeData(body, ctorcalls, tname, ClassObj)
            }
          }
          case q"..$mods object $name extends $template" => template match {
            case template"{ ..$stats } with ..$ctorcalls { $param => ..$body }" => {
              acc.updateTreeData(body, ctorcalls, Type.Name(name.toString), ObjectObj)
            }
          }
          case _ => acc

        })
      }
    }
    data
  }

  def saveToFile(metricsData: TreeData): Unit = {
    new PrintWriter("treeStructure.js") { write(
      s"var links = ${Json.toJson(metricsData.branches).toString()}; " +
        s"var nodeData = ${Json.toJson(metricsData.nodeStats).toString()}"); close }

  }
}
