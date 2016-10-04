name := "reative-tweet"

scalaVersion := "2.11.8"

val `reative-tweet` =
  (project in file("."))
    .enablePlugins(PlayScala)

libraryDependencies += "com.ning" % "async-http-client" % "1.9.29"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies ++= Seq(
  ws,
  "com.typesafe.play.extras" %% "iteratees-extras" % "1.5.0"
)
