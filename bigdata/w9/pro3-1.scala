import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.types._
//import org.apache.spark.sql.cassandra._

import com.datastax.oss.driver.api.core.uuid.Uuids // com.datastax.cassandra:cassandra-driver-core:4.0.0
import com.datastax.spark.connector._              // com.datastax.spark:spark-cassandra-connector_2.11:2.4.3

object StreamHandler {
	def main(args: Array[String]) {

		// initialize Spark
		val spark = SparkSession
			.builder
			.appName("Project-3")		
			.getOrCreate()

		import spark.implicits._

		// read from Kafka
		val inputDF = spark
			.readStream
			.format("kafka") // org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.5
			.option("kafka.bootstrap.servers", "localhost:9092")
			.option("subscribe", "OPERATIONS")
			.option("subscribe", "RESEARCH")
			.option("subscribe", "SALES")
			.option("subscribe", "ACCOUNTING")
			.load()

		// write dataframe to Cassandra
		val query = inputDF
			.writeStream
			.outputMode("update")
			.format("console")
			.start()

		// until ^C
		query.awaitTermination()
	}
}
