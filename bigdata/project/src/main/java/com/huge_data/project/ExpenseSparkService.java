package com.huge_data.project;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Service;

@Service
public class ExpenseSparkService {

    private final SparkSession spark;

    public ExpenseSparkService(SparkSession spark) {
        this.spark = spark;
    }

    public double getTotalExpense(int empno) {
        Dataset<Row> expensesDF = spark.read()
                .format("org.apache.spark.sql.cassandra")
                .option("keyspace", "huge_data")
                .option("table", "expenses")
                .load()
                .filter(functions.col("user_id").equalTo(empno));

        Row result = expensesDF
                .agg(functions.coalesce(functions.sum("payment"), functions.lit(0.0)))
                .first();

        return Math.round(result.getDouble(0) * 100.0) / 100.0;
    }
}