name := """busy-example"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"
includeFilter in gzip := "*.html" || "*.css" || "*.js"

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
  cache,
  "com.viajobien" %% "busy" % "0.1.0",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.2.0" exclude ("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.3.11",
  "org.webjars" % "angular-ui-router" % "0.2.13",
  "org.webjars" % "angular-ui-bootstrap" % "0.12.0"
)

pipelineStages := Seq(digest, gzip)

scalacOptions ++= Seq(
  "-feature",
  "-language:postfixOps",
  "-language:reflectiveCalls"
)

