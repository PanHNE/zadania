name := "tasks"
 
version := "1.0" 
      
lazy val `tasks` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc ,
  ehcache ,
  ws ,
  specs2 % Test ,
  guice ,
  "org.apache.poi" % "poi" % "3.17",
  "org.apache.poi" % "poi-ooxml" % "3.17",
  "org.webjars.bower" % "jquery" % "3.4.1"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      