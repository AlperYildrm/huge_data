import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.cassandra._

spark.conf.set("spark.cassandra.connection.host", "127.0.0.1")

val inputDF = spark
  .readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", "localhost:9092")
  .option("subscribe", "OPERATIONS,RESEARCH,SALES,ACCOUNTING")
  .option("startingOffsets", "earliest")
  .load()

val parts = split(col("value").cast("string"), ",")

val parsedDF = inputDF
  .filter(size(parts) >= 6)
  .select(
    trim(parts.getItem(0)).cast("int").as("user_id"),
    trim(parts.getItem(1)).as("date_time"),
    trim(parts.getItem(2)).as("description"),
    trim(parts.getItem(3)).as("type"),
    trim(parts.getItem(4)).cast("int").as("count"),
    trim(parts.getItem(5)).cast("double").as("payment")
  )

val query = parsedDF
  .writeStream
  .trigger(Trigger.ProcessingTime("5 seconds"))
  .foreachBatch { (batchDF: DataFrame, batchID: Long) =>
    val rowCount = batchDF.count()
    println(s"Writing batch $batchID ($rowCount rows) to huge_data.expenses")
    if (rowCount > 0) {
      batchDF.write
        .cassandraFormat("expenses", "huge_data")
        .mode("append")
        .save()
    }
  }
  .option("checkpointLocation", sys.props("user.home") + "/bigdata/spark-checkpoint")
  .outputMode("append")
  .start()

query.awaitTermination()
