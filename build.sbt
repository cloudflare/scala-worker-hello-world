enablePlugins(ScalaJSPlugin)

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "2.6.0"
)

scalaJSUseMainModuleInitializer := true

Compile / fullOptJS / artifactPath := baseDirectory.value / "index.js"
