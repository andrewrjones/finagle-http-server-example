organization := "com.andrew-jones"

name := "Finagle Example"

version := "0.1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.20.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "junit" % "junit" % "4.11" % "test"
)