These folders are copies of tools from a team member's machine, not part of the project code:

  kafka/              Kafka 3.9.0 (with its server logs)
  spark/              Spark 3.5.5 (the full download, including its Kubernetes Dockerfiles)
  cassandra/          Cassandra 5.0.9 (with the data and commit logs it wrote inside its own folder)
  spark-checkpoint/   an old checkpoint of the stream; its Kafka offsets belong to that machine's Kafka

The project does not need them. Install Kafka, Spark, Cassandra and Hadoop on your own machine, then
start everything as described in the study guide. Nothing in this project runs in Docker.
