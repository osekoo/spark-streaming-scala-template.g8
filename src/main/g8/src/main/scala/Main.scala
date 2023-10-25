import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object Main {
  def main(args: Array[String]): Unit = {

    def main(args: Array[String]): Unit = {
      val spark: SparkSession = SparkSession.builder()
        .appName("$name$")
        .getOrCreate()

      // defining input stream data type (fields of kafka event)
      val definitionSchema = new StructType()
        .add(StructField("<input_field>", StringType, nullable = true))

      // reading data from kafka topic
      val inputStream = spark.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", "$kafka_broker$")
        .option("subscribe", "$kafka_topic_input$")
        .load()

      // Udf function to use to transform our input data
      val transformationUdf = udf((definition: String) => {
        // implement kafka event transformation here
      })

      // perform transformation here
      val outputDf = inputStream.selectExpr("cast(value as string)")
        .select(from_json(col("value"), definitionSchema).as("data"))
        .select(col("data.<input_field>"),
          transformationUdf(col("data.<input_field>")) // applying transformation on your <field>
            .as("<output_field>")
        )

      // displaying the transformed data to the console for debug purpose
      val streamConsoleOutput = outputDf.writeStream
        .outputMode("append")
        .format("console")
        .option("truncate", "false")
        .start()

      // sending the transformed data to kafka
      val kafkaDf = outputDf
        .select(
          to_json(struct(col("<output_field>"))).as("value")) // compute a mandatory field `value` for kafka
        .writeStream
        .outputMode("append")
        .format("kafka")
        .option("kafka.bootstrap.servers", "$kafka_broker$")
        .option("topic", "$kafka_topic_output$")
        .option("checkpointLocation", "/tmp/checkpoint") // required in kafka mode (the behaviour hard coded in the api!)
        .start()

      // waiting the query to complete (blocking call)
      streamConsoleOutput.awaitTermination()
    }
  }
}
