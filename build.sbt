name := """busy-example"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies += "com.viajobien" %% "busy" % "0.1.0"

scalacOptions ++= Seq(
  "-feature",
  "-language:postfixOps",
  "-language:reflectiveCalls"
)

