package Fetch_Assesment

import Fetch_Assesment.Postgres_Main._
import Fetch_Assesment.Run_Program.queryApp

import scala.annotation.tailrec

object menu_options {
  val Spark_Manager = new spark_manager()

  @tailrec
  def Main_Page(): Unit = {
    println("===" * 20 + "WELCOME TO NICK's ETL APPLICATION" + "===" * 20)
    println(
      """
        |        MAIN PAGE 📌
        |[1] Postgres Database
        |[2] Aws Localstack Sqs Queue
        |[3] Admin
        |[0] Logout
        |
        |Please Select An Option: """.stripMargin)
    val main_page = scala.io.StdIn.readLine()

    /*Case Matching Login Features To Option Chosen*/
    main_page match {
      case "1" =>
        login_menu()

      case "2" =>
        Run_Program.delay(1000)
        Localstack_connection.Publish_Iterable_Messages()

      case "3" =>
        Run_Program.delay(1000)
        Admin()

      case "0" =>
        Run_Program.delay(1000)
        Spark_Manager.closeSpark()
        Run_Program.delay(1000)
        Localstack_connection.ShutdownAPI()
        Run_Program.delay(1000)
        ExecutionContext_shutdown()
        Run_Program.delay(1000)
        println("Logout Successful... 📴\nGoodbye!!")
        sys.exit()

      case _ =>
        println("Wrong Entry, Choose Another Option [1][2][3][0]:")
        Run_Program.delay(1000)
        Main_Page()
    }
  }

    /*Defined A Function For Login Features*/
    @tailrec
  def login_menu(): Unit = {
      println("===" * 22 + "POSTGRES DATABASE SECTION" + "===" * 22)
      println(
        """
          |   POSTGRES PAGE 📌
          |[1] Login
          |[2] Signup
          |[3] Forgot Password
          |[4] Delete Account
          |[0] Main Page
          |
          |Please Select An Option: """.stripMargin)
      val input = scala.io.StdIn.readLine()

      /*Case Matching Login Features To Option Chosen*/
      input match {
        case "1" =>
          Login_Postgres()
        case "2" =>
          Signup_Postgres()
        case "3" =>
          Forgot_Password()
        case "4" =>
          Delete_Account()
        case "0" =>
          ExecutionContext_shutdown()
          println("Returning To Main Page...")
          Run_Program.delay(1000)
          Main_Page()
        case _ =>
          println("Wrong Entry, Choose Another Option [1][2][3][4][0]:")
          Run_Program.delay(1000)
          login_menu()

      }
    }

  /*Defined A Function For Menu Features*/
  @tailrec
  def ETL_MENU(): Unit = {
    println("===" * 20 + "EXTRACT TRANSFORM AND LOAD SECTION" + "===" * 20)
    println(
      """
        |       ETL MENU OPTIONS 📌
        |[1] Extract Raw Data With Masked Sensitive Information's From Data Source
        |[2] Data Analysis
        |[0] Return To Postgres Page
        |
        |Please Select Your Preference From The Above Options: """.stripMargin)
    val ETL = scala.io.StdIn.readLine()

    /*Case Matching Menu Features To Option Chosen */
    ETL match {
      case "1" =>
        println("===" * 25 + "SPARK SESSION" + "===" * 25)
        println("\nExtracting Raw Json Dataset Limited To 20 Rows!!!")
        queryApp()
        println("---" * 30)
        Run_Program.pause_details_menu(1000)

      case "2" =>
        Run_Program.delay(1000)
        Q1()
        ETL_MENU()

      case "0" =>
        Spark_Manager.closeSpark()
        println("Returning To Postgres Page...")
        Run_Program.delay(2000)
        login_menu()

      case _ =>
        println("Wrong Entry, Choose Another Option [1][2][3][0]:")
        Run_Program.delay(1000)
        ETL_MENU()

    }
  }

  /*Defined A Function For Sub-Menu Query Features*/
  @tailrec
  def Q1(): Unit = {
    println("===" * 22 + "DATA ANALYSIS SECTION" + "===" * 22)
    println(
      """
        |          DEVICE TYPE PREFERENCE ANALYSIS 📌
        |[a] Specified Location Preference
        |[b] Unspecified Location Preference
        |[c] Return To Postgres Page
        |
        |Please Select Your Preference From The Above Options: """.stripMargin)
    val analysis: String = scala.io.StdIn.readLine()

    /*Case Matching Sub-Menu Query Features To Option Chosen*/
    analysis match {
      case "a" =>
        println("===" * 30)
        Spark_Manager.query(queryNumber = "Q1_a")
        Run_Program.pause_Q(1000)

      case "b" =>
        println("===" * 30)
        Spark_Manager.query(queryNumber = "Q1_b")
        Run_Program.pause_Q(1000)

      case "c" =>
        println("Returning To ETL MENU OPTIONS...")
        Run_Program.delay(2000)
        ETL_MENU()

      case _ =>
        println("Wrong Entry, Choose Another Option [a][b][c]: ")
        Run_Program.delay(1000)
        Q1()
    }
  }
}


