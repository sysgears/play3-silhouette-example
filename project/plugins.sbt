// Comment to get more information during initialization
logLevel := Level.Warn

addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.1")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.3")

ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)
