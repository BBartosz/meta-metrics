name := "meta-metrics"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalameta" %% "scalameta" % "1.1.0"

libraryDependencies ++= Seq(
  "org.scalameta" %% "scalameta" % "1.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.10",
  "com.typesafe.play" %% "play-json" % "2.5.8")