package Fetch_Assesment

/*Imported Dependencies Needed To Optimize The Application*/
import org.apache.spark.sql.SparkSession
import vegas.DSL.Vegas
import vegas.sparkExt.VegasSpark
import vegas.{Bar, Nom, Nominal, Scale}

import scala.Console.println

class data_analysis {

  /*Defined A Function For SparkSql Query Analysis*/
  def DeviceTypeWithLocation(spark: SparkSession): Unit ={
    println("DEVICE TYPE PREFERENCE IN SPECIFIED LOCATION Limited To 10 Rows")
    val Dataset = spark.read.format("org.apache.spark.sql.execution.datasources.json.JsonFileFormat")
      .option("multiline", value = true)
      .option("inferSchema", value = true)
      .load("src/input/sample_data.json")
    Dataset.createOrReplaceTempView("App_version")

    val AndroidAndIOS = spark.sql("select *, substring(device_id, 1,4)" +
      "||regexp_replace(substring(device_id,4,length(device_id)-4)," +
      "'[A-Za-z0-9_.]','*')|| substring(device_id, -1, 4)" +
      "masked_device_id, " +
      "substring(ip,1,4)|| regexp_replace(substring(ip,4,length (ip)-4), '[A-Za-z0-9_.]','*')||substring(ip,-1,4) masked_ip" +
      " from App_version where locale is not null LIMIT 10").drop("device_id", "ip").sort("device_type")

    /*QUERY 1A VEGAS VISUALIZATION*/
    Vegas("android_locale_details")
      .withDataFrame(AndroidAndIOS)
      .mark(Bar)
      .encodeX("app_version", Nom)
      .encodeY("device_type", Nom)
      .encodeColor("locale", Nominal, scale = Scale(rangeNominals =
        List("#f21beb", "#1b49f2", "#1bf25c")))
      .show
    AndroidAndIOS.show()

    println("---" * 30)
    Run_Program.delay(2000)
  }

  /*Defined A Function For SparkSql Query Analysis*/
  def DeviceTypeWithNoLocation(spark: SparkSession): Unit = {
    println("DEVICE TYPE PREFERENCE WITH NO SPECIFIED LOCATION Limited To 10 Rows")

    val Dataset = spark.read.format("org.apache.spark.sql.execution.datasources.json.JsonFileFormat")
      .option("multiline", value = true)
      .option("inferSchema", value = true)
      .load("src/input/sample_data.json")
    Dataset.createOrReplaceTempView("App_version")

  val AndroidAndIOS_locale = spark.sql("select *, substring(device_id, 1,4)" +
  "||regexp_replace(substring(device_id,4,length(device_id)-4)," +
  "'[A-Za-z0-9_.]','*')|| substring(device_id, -1, 4)" +
  "masked_device_id, " +
  "substring(ip,1,4)|| regexp_replace(substring(ip,4,length (ip)-4), '[A-Za-z0-9_.]','*')||substring(ip,-1,4) masked_ip" +
  " from App_version where locale is null LIMIT 10").drop("device_id", "ip").sort("device_type")

    /*QUERY 1A VEGAS VISUALIZATION*/
    Vegas("AndroidAndIOS_locale")
      .withDataFrame(AndroidAndIOS_locale)
      .mark(Bar)
      .encodeX("app_version", Nom)
      .encodeY("device_type", Nom)
      .encodeColor("device_type", Nominal, scale = Scale(rangeNominals =
        List("#f21beb", "#1b49f2", "#07faf1")))
      .show
    AndroidAndIOS_locale.show()

    println("---" * 30)
    Run_Program.delay(2000)
  }
}
