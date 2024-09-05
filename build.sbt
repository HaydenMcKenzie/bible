name := "Bible"

version := "0.1"

scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.3.2",
  "org.apache.spark" %% "spark-sql" % "3.3.2",
  "org.apache.spark" %% "spark-mllib" % "3.3.2",
  "org.apache.spark" %% "spark-streaming" % "3.3.2",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "org.json4s" %% "json4s-native" % "4.0.7"
)