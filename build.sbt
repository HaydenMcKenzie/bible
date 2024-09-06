enablePlugins(ScalaJSPlugin)

name := "Bible"

version := "0.1"

scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "Bible",
    version := "0.1",
    scalaVersion := "2.13.12",

    // Library dependencies
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalatra" %% "scalatra" % "2.8.2",
      "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.12.340",
      "org.json4s" %% "json4s-native" % "4.0.7",
      "org.scala-js" %%% "scalajs-dom" % "2.0.0", // Use the latest scalajs-dom version
      "com.softwaremill.sttp.client3" %%% "core" % "3.8.3"
    ),

    // Resolvers
    resolvers ++= Seq(
      "Maven Central" at "https://repo1.maven.org/maven2/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
    )
  )