#!/usr/bin/bash

echo 'Spark will be started'

/usr/local/hadoop/sbin/start-dfs.sh
/usr/local/hadoop/sbin/start-yarn.sh
/usr/local/spark/sbin/start-all.sh
