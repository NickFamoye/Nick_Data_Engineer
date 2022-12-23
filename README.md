# ETL application
This is a Back-end ETL application with features that conglomerates, Postgres database, Localstack which is a cloud service emulator with provision to run AWS application entirely on your local machine without connecting to a remote cloud provider, Spark with features to read Json files, mask sensitive information’s and enables scalable analysis and ease use for Data analytics, Data scientist etc. and finally Vegas for data visualization.

This application has the features and capacity to analyze and deduce empirical facts for Real-Time Diagnostic and Descriptive Purposes and also Futuristic Prescriptive and Predictive Purposes.

## Programming Language and Technologies Used
⚛ Scala - version 2.11.12

⚛ Java - version 1.8.1 as the SDK

⚛ Sbt – version 1.6.2

⚛ Spark SQL – version 2.4.8

⚛ Postgresql – version : latest

⚛ Localstack – version: latest

⚛ Docker Desktop- version 4.15.0

⚛ Slick Database Library In Scala


## Integrated Development Environment

⚛ Intellij - 2022 version

## Menu Features

          MAIN PAGE 📌
[1] Postgres Database

[2] Aws Localstack Sqs Queue

[3] Admin

[0] Logout
   
⚛ Postgress Database 

    POSTGRES PAGE 📌
[1] Login

[2] Signup

[3] Forgot Password

[4] Delete Account

[0] Main Page

⚛ login/Signup To Postgress Database To View ETL MENU OPTIONS with SparkSQL Analysis On Dataset 

        ETL MENU OPTIONS 📌
[1] Extract Raw Data With Masked Sensitive Information's From Data Source

[2] Data Analysis

[0] Return To Postgres Page

⚛ Data Analysis Sub Menu

          DEVICE TYPE PREFERENCE ANALYSIS 📌
[a] Specified Location Preference

[b] Unspecified Location Preference

[c] Return To Postgres Page

⚛ Using Slick Library To Accomplish CRUD Operation Task Such As :

 - Signup New User Into Postgres Database
 
 - update Password In Postgres Database If Forgotten
 
 - Delete User Information In Postgres Database
  
 ⚛ Aws Localstack Sqs Queue
 
         LOCALSTACK SQS QUEUE OPTIONS 📌
[a] Publish A Single Messages

[b] Return To Main Page

⚛ Publish A Single Messages Sub Menu

     What Would You Like To Do?
[1] Delete The Above Single Message Body

[2] Navigate Back To Localstack SQS Queue Options

⚛ Admin - Admmin has the super user priviledge to delete the entire information in postgres database 

  Administrative Privilege
[1] Delete All User Login Information

[2] Delete All Multiple Data Table Information

[3] Return To Main Page

⚛ Logout- it ensures all services are closed or shutdown before logging out

Spark Session closed...

sqsClient Closed...

Execution Context shutdown... 

Logout Successful... 📴

Goodbye!!

To-do list

⚛ Ensure Secured Password Encryption.

⚛ Add more Aws Localstack features .

⚛ Keep improving and Writting Efficient, Standard and Scalable Applications.

## Getting Started

⚛ Installed the correct versions of Scala (2.11.12), Spark (2.4.8), JDK (1.8), and build.sbt, 
and I strongly advice not to update the build.sbt versions even if prompted to avoid library version compatibility errors.

⚛ Set up your docker-compose.yml for both Localstack and Postgres. Ensure that you include HOSTNAME_EXTERNAL=localstack in 
the localstack environment and the volume for both postgres and localstack are pointing towards the local init-script files withing db and 
localstack_bootstrap directories. Run (docker-compose up) In Intellij Terminal to set up the database tables.

version: '4.15.0'

services:

    localstack:
    
      container_name: "${LOCALSTACK_DOCKER_NAME-sqs_queue}"
      
      image: localstack/localstack:latest
      
      restart: always
      
      ports:
      
        - "4566:4566"   # LocalStack Gateway
      environment:
      
        - SERVICES = sqs, sns
        - DEBUG=${DEBUG-}
        - DOCKER_HOST=unix:///var/run/docker.sock
        - DEFAULT_REGION = us-east-1
        - EDGE_PORT=4566
        - LEGACY_INIT_DIR=1.
        - HOSTNAME_EXTERNAL=localstack
        
      volumes:
        - ./localstack_bootstrap:/docker-entrypoint-initaws.d/
        - /var/lib/localstack.
        - /var/run/docker.sock:/var/run/docker.sock

    db:
    
      image: postgres:latest
      restart: always
      environment:
        - POSTGRES_USER=postgres
        - POSTGRES_PASSWORD=admin
        
      ports:
        - '5432:5432'
        
      volumes:
      
        - db:/var/lib/postgresql11/data
        - ./db/init-scripts.sql:/docker-entrypoint-initdb.d/scripts.sql
        
volumes:

  db:
  
    driver: local

⚛ Create resources directory between main and scala, then set up an application.conf file for postgress 
using HikariCP as the connection pool.

postgres = {

  connectionPool = "HikariCP"
  
  dataSourceClass = "org.postgresql.ds.PGSimpleDataSource"
  
  properties = {
  
    serverName = "localhost"
    portNumber = "5432"
    databaseName = "postgres"
    user = "postgres"
    password = "admin"   
  }
 
  numThreads = 10
  
}

⚛ Create 2 directories from directly from the project, name them db and localstack_bootstrap, within db directory 
create init-script.sql file and within localstack_bootstrap create sqs_bootstrap.sh. 

⚛ Download all code files, including build.sbt.

⚛ Slick will be using the JDBC connection implementation from the PGSimpleDataSource to connect to the database.

⚛ Configure IntelliJ or IDE of your choice to work with the appropriate version of JDK.

⚛ Run from within preferred IDE. 

## Attributes

I will like to especially appreciate Abby Dede and Swadhina Pradhan for their amazing support throughout the project. 

## Contact Me
[<img src='https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/linkedin.svg' alt='linkedin' height='40'>](https://www.linkedin.com/in/nicholas-famoye/)
[<img src='https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/github.svg' alt='github' height='40'>](https://github.com/NickFamoye) 
