import com.typesafe.sbt.SbtScalariform._

import scalariform.formatter.preferences._

name := "auth-with-play-silhouette-example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.12"

resolvers += Resolver.jcenterRepo

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

val playSilhouetteVersion = "10.0.0"
val slickVersion = "3.4.1"
val playSlickVersion = "6.0.0"

libraryDependencies ++= Seq(
  "org.playframework.silhouette" %% "play-silhouette" % playSilhouetteVersion,
  "org.playframework.silhouette" %% "play-silhouette-password-bcrypt" % playSilhouetteVersion,
  "org.playframework.silhouette" %% "play-silhouette-persistence" % playSilhouetteVersion,
  "org.playframework.silhouette" %% "play-silhouette-crypto-jca" % playSilhouetteVersion,
  "net.codingwell" %% "scala-guice" % "6.0.0",
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.playframework" %% "play-slick" % playSlickVersion,
  "org.playframework" %% "play-slick-evolutions" % playSlickVersion,
  //it's org.postgresql.ds.PGSimpleDataSource dependency
  "org.postgresql" % "postgresql" % "42.7.1",
  guice,
  filters
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  //"-Xlint", // Enable recommended additional warnings.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  // Play has a lot of issues with unused imports and unsued params
  // https://github.com/playframework/playframework/issues/6690
  // https://github.com/playframework/twirl/issues/105
  "-Xlint:-unused,_"
)

//********************************************************
// Scalariform settings
//********************************************************

scalariformAutoformat := true

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(FormatXml, false)
  .setPreference(DoubleIndentConstructorArguments, false)
  .setPreference(DanglingCloseParenthesis, Preserve)
