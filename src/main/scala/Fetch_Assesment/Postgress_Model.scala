package Fetch_Assesment

 /*Imported Dependencies Needed To Optimize The Application*/
import java.time.LocalDate

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag


  /*Created A Case Class To Pattern Match Attributes And It's Column*/
case class LoginDatabase(id: Long, username: String, password: String)
case class MultipleDataDatabase(user_id: Long, device_type: String, ip: Int, device_id: Int, locale: String, app_version: Int, create_date: LocalDate)

  /*Created An Object To Instantiate Database Tables Features*/
object SlickTables{

    class LoginTable(tag: Tag) extends Table[LoginDatabase](tag, Some("logindatabase") /* <- schema name */ , "LoginDatabase") {
      private def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
      def username = column[String]("username")
      def password = column[String]("password")
      def signup_date = column[LocalDate]("signup_date")

      /*Mapping Function to the case class*/
      override def * = (id, username, password) <> (LoginDatabase.tupled, LoginDatabase.unapply)
    }

    class multipledataTable(tag: Tag) extends Table[MultipleDataDatabase](tag, Some("multipledatadatabase")/* <- schema name */, "MultipleDataDatabase"){
     private def user_id = column[Long]("user_id", O.PrimaryKey)
      private def device_type = column[String]("device_type")
      private def ip = column[Int]("ip")
      private def device_id = column[Int]("device_id")
      private def locale = column[String]("locale")
      private def app_version = column[Int](" app_version")
      private def create_date = column[LocalDate](" create_date")

      /*Mapping Function to the case class*/
      override def * = (user_id, device_type, ip, device_id, locale, app_version, create_date) <> (MultipleDataDatabase.tupled, MultipleDataDatabase.unapply)
    }

    /*API entry point*/
    lazy val loginTable = TableQuery[LoginTable]
    lazy val multipledataTable = TableQuery[multipledataTable]
}
