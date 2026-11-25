#!/usr/bin/env bash
# Creates what the project uses, with the names in the code:
#   PostgreSQL  database huge_data: tables dept and emp, filled from data/dept.csv and data/emp.csv
#   Cassandra   keyspace huge_data, table expenses
#   HDFS        folder /swe307/images with the photos from images/
#
# First, once, as the PostgreSQL admin (application.properties uses postgres / alper):
#   sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'alper';" -c "CREATE DATABASE huge_data;"
# PostgreSQL, Cassandra and HDFS must be running, and psql, cqlsh and hdfs on the PATH.
# Safe to run again: it never loads a table that already has rows.
set -euo pipefail
cd "$(dirname "$0")/.."

export PGPASSWORD=alper PGOPTIONS='-c client_min_messages=warning'
PSQL=(psql -h localhost -U postgres -d huge_data -v ON_ERROR_STOP=1 -q)

echo "PostgreSQL: tables dept and emp"
"${PSQL[@]}" -f db/postgres.sql
if [ "$("${PSQL[@]}" -tAc 'SELECT count(*) FROM dept')" = "0" ]; then
    "${PSQL[@]}" -c "\copy dept FROM 'data/dept.csv' WITH (FORMAT csv, HEADER true)"
fi
if [ "$("${PSQL[@]}" -tAc 'SELECT count(*) FROM emp')" = "0" ]; then
    "${PSQL[@]}" -c "\copy emp FROM 'data/emp.csv' WITH (FORMAT csv, HEADER true)"
fi
echo "  dept: $("${PSQL[@]}" -tAc 'SELECT count(*) FROM dept') rows, emp: $("${PSQL[@]}" -tAc 'SELECT count(*) FROM emp') rows"

echo "Cassandra: keyspace huge_data, table expenses"
cqlsh -f db/cassandra.cql

echo "HDFS: /swe307/images"
hdfs dfs -mkdir -p /swe307/images
hdfs dfs -put -f images/*.jpg /swe307/images/
echo "  $(hdfs dfs -ls /swe307/images | grep -c '\.jpg$') photos"
