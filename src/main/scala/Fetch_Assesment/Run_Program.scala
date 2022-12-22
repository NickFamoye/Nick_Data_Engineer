package Fetch_Assesment

object Run_Program {
  val Spark_Manager = new spark_manager

  /*Defined A Function To Display Developers At The Beginning Of The Application*/
  def Developer(n: String): Unit = {
    n match {
      case stars =>
        println(stars * 5)
        println("Nicholas Famoye: Big Data Engineer")
    }
  }
  Developer(n = "⭐")

  /*Defined A Function To Create A Temporary Delay Between Menu Screens*/
  def delay(milliseconds: Int): Unit = {
    Thread.sleep(milliseconds)
  }

  /*Defined A Function To Create A Temporary Pause For Output In The Console*/
  def pause(milliseconds: Int): Unit = {
    Thread.sleep(milliseconds)
    println("\nPress Any Key And Enter To Navigate Back To Main Page: ")
    scala.io.StdIn.readLine()
    menu_options.login_menu()
  }

  def pause_details_menu(milliseconds: Int): Unit = {
    Thread.sleep(milliseconds)
    println("\nPress Any Key And Enter To Navigate Back To ETL Menu Page ")
    scala.io.StdIn.readLine()
    menu_options.ETL_MENU()
  }

  def pause_sqsqueue(milliseconds: Int): Unit = {
    Thread.sleep(milliseconds)
    println("\nPress Any Key And Enter To Navigate Back To AWS Localstack SQS Queue Page ")
    scala.io.StdIn.readLine()
//    menu_options.Q1()
  }

  def pause_Q(milliseconds: Int): Unit = {
    Thread.sleep(milliseconds)
    println("\nPress Any Key And Enter To Navigate Back To Data Analysis Page ")
    scala.io.StdIn.readLine()
    menu_options.Q1()
  }

  /*Defined A Function To Instantiate SparkSql Table*/
  def queryApp(): Unit = {
    Spark_Manager.SparkSQL_Table()
  }

  /*Defined Main Function To Run The Program*/
  def main(args: Array[String]): Unit = {
    menu_options.Main_Page()
  }
}

