package Fetch_Assesment

/*Imported Dependencies Needed To Optimize The Application*/
import java.util.concurrent.{ExecutorService, Executors}
import Fetch_Assesment.menu_options.{ETL_MENU, login_menu}
import scala.Console.println
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/*Created An Executor Object to Handle Success and Failure Exception*/
object MyExecutionContext {
  val executor: ExecutorService = Executors.newFixedThreadPool(10)
  implicit val ex: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}
/*Created An Object To Instantiate Login And Review Features*/
private object Postgres_Main {

  import MyExecutionContext._
  import slick.jdbc.PostgresProfile.api._

  /*Defined A Function To Give Admin Authority to Delete All Information In Postgres Database*/
  def Admin(): Unit = {
    println("---" * 30)
    println(
      """
        |   Administrative Privilege
        |[1] Delete All User Login Information
        |[2] Delete All Multiple Data Table Information
        |[3] Return To Main Page
        |
        |To Delete Choose An Option:""".stripMargin)
    val delete_all_account: String = scala.io.StdIn.readLine()

    /*Case Matching Delete Features To Option Chosen*/
    delete_all_account match {
      case "1" =>
        postgres_connection.db.run(SlickTables.loginTable.delete)
        println("All User Login Information Are Deleted Successfully From Database!!")
        Run_Program.pause(1000)
        login_menu()

      case "2" =>
        postgres_connection.db.run(SlickTables.multipledataTable.delete)
        println("All AWS SQS Queue User Information Are Deleted Successfully From Database!!")
        Run_Program.pause(1000)
        login_menu()

      case "3" =>
        Run_Program.delay(1000)
        login_menu()

      case _ =>
        println("Wrong Entry, Please Choose Another Option [1][2][3]:")
        Run_Program.delay(1000)
        Admin()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Login and Confirm Your Information In Postgres Database*/
  def Login_Postgres(): Unit = {
    Run_Program.delay(1000)
    println("---" * 30)
    println(
      """Login To Access The ETL Menu
        |
        |Enter Your Username: """.stripMargin)
    val username_in: String = scala.io.StdIn.readLine()

    println("Password: ")
    val password_in: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection and Filtering your information In Postgres Database*/
    val Login_PostgresFuture: Future[Seq[LoginDatabase]] = postgres_connection.db.run(SlickTables.loginTable
      .filter(_.password === password_in).filter(_.username === username_in).result)

    /*Future Concurrent Success and Failure Block*/
    Login_PostgresFuture.onComplete {
      case Success(password) =>
        val username = username_in
        if (password.nonEmpty && username.nonEmpty) {
          println(s"Successful Login $username_in!")

          Run_Program.delay(1000)
          ETL_MENU()
        }
        else println("---" * 30)
        println(
          s"""
             |Username Or Password Incorrect!!
             |
             |[1] Try Again
             |[2] Signup
             |[3] Forgot Password
             |
             |Choose An Option: """.stripMargin)
        val input = scala.io.StdIn.readLine()

        input match {

          case "1" =>
            Run_Program.delay(1000)
            Login_Postgres()

          case "2" =>
            Run_Program.delay(1000)
            Signup_Postgres()

          case "3" =>
            Run_Program.delay(1000)
            Forgot_Password()

          case _ =>
            println("Wrong Entry, Please Choose Another Option [1][2][3]:")
            Run_Program.delay(1000)
            Login_Postgres()
        }

      case Failure(ex) => println(s"Diagnosis: $ex")
        Run_Program.delay(1000)
        Login_Postgres()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Signup and Store Your Information In Postgres Database*/
  def Signup_Postgres(): Unit = {
    Run_Program.delay(1000)
    println("---" * 30)
    println(
      """Sign Up To Access The ETL Menu
        |
        |Enter Your Username: """.stripMargin)
    val username_new: String = scala.io.StdIn.readLine()

    println("Password: ")
    val password_new: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection and Query description to Grab your information*/
    val loginDatabaseOption: LoginDatabase = LoginDatabase(0, s"$username_new", s"$password_new")
    val queryDescription = SlickTables.loginTable += loginDatabaseOption
    val insert_futureId: Future[Int] = postgres_connection.db.run(queryDescription)

    /*Future Concurrent Success and Failure Block*/
    insert_futureId.onComplete {
      case Success(newUsername) => println(s"$newUsername Signup Completed! ")
        Run_Program.delay(1000)
        ETL_MENU()

      case Failure(_) => println(s"Sorry Username=($username_new) Already Exists\nTry Again!")
        Run_Program.delay(1000)
        Signup_Postgres()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Update Password Information In Postgres Database*/
  def Forgot_Password(): Unit = {
    Run_Program.delay(1000)
    println("---" * 30)

    println("Forgot Password Please Enter Your\nUsername: ")
    val username_in: String = scala.io.StdIn.readLine()

    println("Enter Your New Password: ")
    val password_in: String = scala.io.StdIn.readLine()

    val id_in: Long = scala.io.StdIn.hashCode()

    /*Established Postgres Connection To Update your Password In Postgres Database*/
    val loginDatabaseOption: LoginDatabase = LoginDatabase(id_in, username_in, password_in)

    val queryDescriptor = SlickTables.loginTable.filter(_.username === s"$username_in")
      .update(loginDatabaseOption.copy(password = s"$password_in"))
    val update_futureId: Future[Int] = postgres_connection.db.run(queryDescriptor)

    /*Future Concurrent Success and Failure Block*/
    update_futureId.onComplete {
      case Success(username) =>
        println("---" * 30)
        if (username == 0) {
          println(
            """
              |Failed To Update Password!! Username Not Found!!
              |[1] Try Again
              |[2] Return To Main Page
              |
              |Choose An Option: """.stripMargin)
          val input = scala.io.StdIn.readLine()

          input match {

            case "1" =>
              Run_Program.delay(1000)
              Forgot_Password()

            case "2" =>
              Run_Program.delay(1000)
              login_menu()

            case _ =>
              println("Wrong Entry, Please Choose Another Option [1][2]:")
              Run_Program.delay(1000)
              Forgot_Password()
          }
        }
        else
          println(s"$username New Password Updated Successful!")
        Run_Program.delay(1000)
        login_menu()

      case Failure(_) => println("Please Try A More Secured Password!")
        Run_Program.delay(1000)
        Forgot_Password()
    }
    Thread.sleep(50000)
  }

  /*Defined A Function To Delete Filtered Information In Postgres Database*/
  def Delete_Account(): Unit = {
    Run_Program.delay(1000)
    println("---" * 30)
    println("To Permanently Delete Your Account\nEnter Your Username: ")
    val username_in: String = scala.io.StdIn.readLine()

    println("Enter Your Password: ")
    val password_in: String = scala.io.StdIn.readLine()

    /*Established Postgres Connection To Delete Filtered Information In Postgres Database*/
    val delete_account: Future[Int] = postgres_connection.db.run(SlickTables.loginTable.
      filter(_.username === username_in).filter(_.password === password_in).delete)

    /*Case Matching Delete Features To Option Chosen*/
    delete_account.onComplete {
      case Success(username) =>
        if (username == 0) {
          println(
            """
              |Failed To Delete!! Username Or Password Incorrect!!
              |[1] Try Again
              |[2] Return To Main Page
              |
              |Choose An Option: """.stripMargin)
          val input = scala.io.StdIn.readLine()

          input match {

            case "1" =>
              Run_Program.delay(1000)
              Delete_Account()

            case "2" =>
              Run_Program.delay(1000)
              login_menu()

            case _ =>
              println("Wrong Entry, Please Choose Another Option [1][2]:")
              Run_Program.delay(1000)
              Delete_Account()
          }
        }
        else println(s"$username User Deleted From Database!!")
        Run_Program.delay(1000)
        login_menu()

      case Failure(ex) =>
        println(s"$ex Failed To Delete")
        Run_Program.delay(1000)
        Delete_Account()
    }
    Thread.sleep(50000)
  }

  /* Defined A Function To shutdown ExecutionContext*/
  def ExecutionContext_shutdown(): Unit = {
    println("Execution Context shutdown... ")
    MyExecutionContext.executor.shutdown()
    Thread.sleep(2000)
  }
}

