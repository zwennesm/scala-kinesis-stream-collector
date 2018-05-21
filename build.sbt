name := "scala-kinesis-consumer"
version := "0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.amazonaws" % "amazon-kinesis-client" % "1.9.1",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.8.3",
  "com.sksamuel.avro4s" %% "avro4s-json" % "1.8.3",
  "com.snowplowanalytics" %% "snowplow-scala-analytics-sdk" % "0.3.0",
  "jp.co.bizreach" %% "aws-s3-scala" % "0.0.13",
)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
)