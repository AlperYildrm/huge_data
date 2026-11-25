package com.huge_data.project.services.impl;

import com.huge_data.project.services.IExpenseSparkService;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

@Service
public class ExpenseSparkServiceImpl implements IExpenseSparkService {

    private final SparkSession spark;

    public ExpenseSparkServiceImpl(SparkSession spark) {
        this.spark = spark;
    }

    // Spark reads the expenses with the RDD API, like the teacher's sc.cassandraTable example.
    // spark.read() would load Spark's SQL parser, which fails next to Hibernate's newer ANTLR.
    @Override
    public double getTotalExpense(int empno) {
        double total = javaFunctions(spark.sparkContext())
                .cassandraTable("huge_data", "expenses")
                .select("payment")
                .where("user_id = ?", empno)
                .mapToDouble(row -> row.getDouble("payment"))
                .sum();

        return Math.round(total * 100.0) / 100.0;
    }
}
