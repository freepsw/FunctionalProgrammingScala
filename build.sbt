name := "FunctionalProgrammingScala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4"
)