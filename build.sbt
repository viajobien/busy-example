name := """busy-example"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.3"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
  ws,
  guice,
  "com.viajobien" %% "busy" % "0.2.0"
)

scalacOptions ++= Seq(
  "-feature",
  "-language:postfixOps",
  "-language:reflectiveCalls"
)

