@echo off

docker compose down -v
docker compose up spark-worker -d
docker compose up $name;format="normalize"$-spark-client
docker compose down -v
