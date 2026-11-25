#!/usr/bin/sh
#
$KAFKA_HOME/bin/kafka-topics.sh --create --topic SALES --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
#
$KAFKA_HOME/bin/kafka-topics.sh --create --topic OPERATIONS --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
#
$KAFKA_HOME/bin/kafka-topics.sh --create --topic RESEARCH --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
#
$KAFKA_HOME/bin/kafka-topics.sh --create --topic ACCOUNTING --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
