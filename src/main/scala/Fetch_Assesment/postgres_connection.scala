package Fetch_Assesment

/*Imported Dependencies Needed To Optimize The Application*/
import org.apache.log4j.{Level, Logger}
import slick.jdbc.JdbcBackend.Database

/*Created An Object To Establish Postgres Connection*/
object postgres_connection {
  val db  = Database.forConfig("postgres")

  /*TURN LOG OFF*/
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  Logger.getLogger("hstore").setLevel(Level.OFF)
  Logger.getLogger("HikariCP").setLevel(Level.OFF)
  Logger.getLogger("HikariCP").setLevel(Level.OFF)
  Logger.getLogger("HikariDataSource").setLevel(Level.OFF)
  Logger.getLogger("org.postgresql.ds.PGSimpleDataSource").setLevel(Level.OFF)
}