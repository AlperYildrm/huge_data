-- Keyspace and table that w9/pro3.scala writes to and the web app reads from.

CREATE KEYSPACE IF NOT EXISTS huge_data
  WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

-- date_time is part of the key: with user_id alone, Cassandra would keep
-- only the last expense of each person
CREATE TABLE IF NOT EXISTS huge_data.expenses (
  user_id     int,
  date_time   text,
  description text,
  type        text,
  count       int,
  payment     double,
  PRIMARY KEY ((user_id), date_time)
);
