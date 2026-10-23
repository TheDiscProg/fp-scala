
ThisBuild / scalaVersion := "3.10.0"

ThisBuild / version := "0.1.0"

ThisBuild / organization := "com.example"

lazy val root = (project in file("."))
  .settings(
    name := "cats-effect-example",

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core"   % "2.13.0",
      "org.typelevel" %% "cats-effect" % "3.7.1"
    )
  )
