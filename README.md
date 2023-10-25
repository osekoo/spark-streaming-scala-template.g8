# Spark Streaming Template (Scala + Kafka)

## Usage:
Execute the following command to create a new spark cluster project with scala:


```shell
sbt new osekoo/spark-streaming-scala-template.g8
cd <project-name>
docker compose up
```

## Requirements
The following frameworks and software are required:
- [sbt](https://www.scala-sbt.org/download.html)
- [docker](https://docs.docker.com/engine/install/)
- [docker-compose](https://docs.docker.com/compose/install/) 

## Notes
The spark cluster is created using [osekoo/spark](https://github.com/osekoo/spark-docker-image) docker image.