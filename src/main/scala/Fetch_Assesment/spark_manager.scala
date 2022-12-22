package Fetch_Assesment

/*Imported Dependencies Needed To Optimize The Application*/
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import scala.language.postfixOps


class spark_manager {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  /*Establishing New Instance Of Data_Analysis Class To map
    Each Query Function To A Specific Case*/
  private val Data_analysis = new data_analysis()

  /*ESTABLISHING SPARK SESSION CONNECTION
  FOR SPARK SQL QUERIES*/
  val spark: SparkSession = SparkSession
    .builder()
    .appName(" FETCH AWS SQS QUEUE ETL")
    .config("spark.master", "local")
    .getOrCreate()

  /*Defined A Function To Read Json Dataset*/
  def SparkSQL_Table(): Unit = {

    val Data_Json = spark.read.format("org.apache.spark.sql.execution.datasources.json.JsonFileFormat")
      .option("multiline", value = true)
      .option("inferSchema", value = true)
      .load("src/input/sample_data.json")
    Data_Json.createOrReplaceTempView("App_version")

    val masked_field = spark.sql("select *, substring(device_id, 1,4)" +
      "||regexp_replace(substring(device_id,4,length(device_id)-4)," +
      "'[A-Za-z0-9_.]','*')|| substring(device_id, -1, 4)" +
      "masked_device_id, " +
      "substring(ip,1,4)|| regexp_replace(substring(ip,4,length (ip)-4), '[A-Za-z0-9_.]','*')||substring(ip,-1,4) masked_ip" +
      " from App_version").drop("device_id", "ip")

    masked_field.show(false)
    println("SCHEMA OUTPUT")
    Data_Json.printSchema()
  }

  /* Defined A Function To End SparkSession*/
  def closeSpark(): Unit = {
    println("Spark Session closed...")
    spark.close()
    Run_Program.delay(1000)
  }

  /*Defined A Function To Case Match The Query Options*/
  def query(queryNumber: String): Unit = {
    queryNumber match {
      case "Q1_a" => Data_analysis.DeviceTypeWithLocation(spark: SparkSession)
      case "Q1_b" => Data_analysis.DeviceTypeWithNoLocation(spark: SparkSession)
    }
  }
}
