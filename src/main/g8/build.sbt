organization := sys.env.get("APP_ORGANIZATION").getOrElse("org.company.dev")

name := sys.env.get("APP_NAME").getOrElse("$name;format="normalize"$") // the project's name

version := sys.env.get("APP_VERSION").getOrElse("$app_version$") // the application version

scalaVersion := sys.env.get("SCALA_VERSION").getOrElse("$scala_version$") // version of Scala we want to use (this should be in line with the version of Spark framework)

crossTarget := baseDirectory.value / "target"

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  "$name;format="normalize"$-$app_version$.jar"
}

val sparkVersion = sys.env.get("SPARK_VERSION").getOrElse("$spark_version$")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion
)
