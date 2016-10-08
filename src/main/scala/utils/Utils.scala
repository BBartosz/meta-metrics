package utils

import java.io.File

object Utils {
  def recursiveListFiles(file: File): Iterable[File] = {
    if (file.exists()){
      val children = new Iterable[File] {
        def iterator = if (file.isDirectory) file.listFiles.iterator else Iterator.empty
      }
      val newFile = if (file.isDirectory) Seq() else Seq(file)
      newFile ++: children.flatMap(recursiveListFiles)
    } else {
      new Iterable[File] {def iterator = Iterator.empty}
    }
  }
}
