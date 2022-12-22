ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"

val AkkaVersion = "2.5.32"
val AkkaHttpVersion = "10.1.15"
val awsJavaSdkVersion = "1.12.351"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % "2.5.32" % Test,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "org.scalatest" %% "scalatest" % "3.2.14" % Test,
  "org.mockito" % "mockito-core" % "4.8.0",
  "com.lightbend.akka" %% "akka-stream-alpakka-sqs" % "2.0.2",
  "com.amazonaws" % "aws-java-sdk" % "1.12.353",
  "com.amazonaws" % "aws-java-sdk-core" % awsJavaSdkVersion,
  "org.springframework" % "spring-context" % "6.0.2",

  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.github.tminglei" %% "slick-pg" % "0.20.4",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.20.4",
  "org.postgresql" % "postgresql" % "42.5.1",

//  "com.github.mrpowers" %% "spark-daria" % "0.39.0",
  "org.apache.spark" %% "spark-core" % "2.4.8",
  "org.apache.spark" %% "spark-sql" % "2.4.8",
  "org.apache.spark" %% "spark-hive" % "2.4.8",

//  "org.apache.poi" % "poi" % "5.2.2",
//  "org.apache.poi" % "poi-ooxml" % "5.2.2",
//  "org.apache.poi" % "poi-ooxml-lite" % "5.2.2",
  "net.liftweb" %% "lift-json" % "3.5.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.17.2",


"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.14.0",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.14.1",

  "au.com.dius" %% "pact-jvm-consumer" % "3.5.24",

  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.7.2",
  "org.apache.hadoop" % "hadoop-auth" % "2.7.2",
  "org.apache.hadoop" % "hadoop-common" % "2.7.2",
  "commons-io" % "commons-io" % "2.4",

  "org.vegas-viz" %% "vegas" % "0.3.11",
  "org.vegas-viz" %% "vegas-spark" % "0.3.11"
)

lazy val root = (project in file("."))
  .settings(
    name := "Nick_Data_Engineer"
  )

