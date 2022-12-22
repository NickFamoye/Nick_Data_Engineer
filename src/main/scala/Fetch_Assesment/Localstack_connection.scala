package Fetch_Assesment

import akka.actor.ActorSystem
import akka.stream.alpakka.sqs.{MessageAttributeName, SqsSourceSettings}
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.{ActorMaterializer, DelayOverflowStrategy, KillSwitches}
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.sqs.model.{DeleteMessageRequest, QueueDoesNotExistException, ReceiveMessageRequest, SendMessageRequest}
import com.amazonaws.services.sqs.{AmazonSQSAsync, AmazonSQSAsyncClientBuilder}
import com.github.matsluni.akkahttpspi.AkkaHttpClient.logger

import scala.Console.println
import scala.annotation.tailrec
import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Localstack_connection {

  case class create(queueUrl: String)

  val sqsEndpoint = "http://localhost:4566/000000000000/queue2"
  val sqsRegion = "us-east-1"
  val sqsAccessKey = "test"
  val sqsSecretKey = "test"

  val credentials = new BasicAWSCredentials(sqsAccessKey, sqsSecretKey)
  val endpointConfiguration = new EndpointConfiguration(sqsEndpoint, sqsRegion)

  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()

  implicit val sqsClient: AmazonSQSAsync = AmazonSQSAsyncClientBuilder
    .standard()
    .withEndpointConfiguration(endpointConfiguration)
    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    .build()

  val settings: SqsSourceSettings = SqsSourceSettings()
    .withWaitTime(20.seconds)
    .withMaxBufferSize(100)
    .withMaxBatchSize(10)
    .withMessageAttribute(MessageAttributeName.create("bar.*"))
    .withCloseOnEmptyReceive(true)
    .withVisibilityTimeout(10.seconds)

  def killSwitch_Error(): Unit = {
    val countingSrc = Source(Stream.from(1)).delay(1.second, DelayOverflowStrategy.backpressure)
    val lastSnk = Sink.last[Int]

    val (killSwitch, last) = countingSrc
      .viaMat(KillSwitches.single)(Keep.right)
      .toMat(lastSnk)(Keep.both).run()

    val error = new RuntimeException("boom!")
    killSwitch.abort(error)

    Await.result(last.failed, 1.second)
  }

  def killSwitch_shutdown(): Unit = {
    val countingSrc = Source(Stream.from(1)).delay(1.second, DelayOverflowStrategy.backpressure)
    val lastSnk = Sink.last[Int]

    val (killSwitch, last) = countingSrc
      .viaMat(KillSwitches.single)(Keep.right)
      .toMat(lastSnk)(Keep.both)
      .run()

    println("Killswitch Is Shutdown")
    killSwitch.shutdown()
    Await.result(last, 2.second)
  }

  def ShutdownAPI(): Unit = {
    println("sqsClient Closed...")
    system.registerOnTermination(sqsClient.shutdown())
  }

  def assertQueueExists(queueUrl: String): Unit =
    try {
      sqsClient.getQueueAttributes(queueUrl, Seq("All").asJava)
      logger.info(s"Queue at $queueUrl found.")
    } catch {
      case queueDoesNotExistException: QueueDoesNotExistException =>
        logger.error(s"The queue with url $queueUrl does not exist.")
//        throw queueDoesNotExistException
    }

  // Use the sqsClient object to perform operations on the SQS queue, such as sending a message, receiving a message, and deleting a message
  @tailrec
  def Publish_Iterable_Messages(): Unit = {
    println("===" * 21 + "LOCALSTACK SQS QUEUE SECTION" + "===" * 21)
    println(
      """
        |        LOCALSTACK SQS QUEUE OPTIONS 📌
        |[a] Publish A Single Messages
        |[b] Return To Main Page
        |
        |Please Select Your Preference From The Above Options: """.stripMargin)
    val input: String = scala.io.StdIn.readLine()

    input match {

      case "a" =>
        println("Please, Input Your Message Body:")
        val Incoming_message: String = scala.io.StdIn.readLine()

        // Send a message to the queue
        val queueUrl = "http://localhost:4566/000000000000/queue2"
        val messageBody = s"$Incoming_message"
        val sendMessageRequest = new SendMessageRequest()
          .withQueueUrl(queueUrl)
          .withMessageBody(messageBody)
        SqsSourceSettings().withCloseOnEmptyReceive(true)
        sqsClient.sendMessage(sendMessageRequest)

        assertQueueExists(queueUrl)

        // Receive a message from the queue
        val receiveMessageRequest = new ReceiveMessageRequest()
          .withQueueUrl(queueUrl)
          .withWaitTimeSeconds(20)
        val receiveMessageResult = sqsClient.receiveMessage(receiveMessageRequest)
        if (receiveMessageResult.getMessages.size() > 0) {
          val message = receiveMessageResult.getMessages.get(0)
          println(s"Received message: ${message.getBody}")

          println(
            """
              |     What Would You Like To Do?
              |[1] Delete The Above Single Message Body
              |[2] Navigate Back To Localstack SQS Queue Options
              |""".stripMargin)
          val options = scala.io.StdIn.readLine()
          options match {
            case "1" =>
              val deleteMessageRequest = new DeleteMessageRequest()
                .withQueueUrl(queueUrl)
                .withReceiptHandle(message.getReceiptHandle)
              sqsClient.deleteMessage(deleteMessageRequest)
              println(s"$messageBody Deleted successfully")
              println("Navigating Back To Localstack SQS Queue Options")
              Run_Program.delay(2000)
              Publish_Iterable_Messages()

            case "2" =>
              println("---" * 30)
              println("Navigating Back To Localstack SQS Queue Options")
              Run_Program.delay(2000)
              Publish_Iterable_Messages()
          }
        }

      case "b" =>
        println("Navigating Back To Main Page")
        Run_Program.delay(2000)
        menu_options.Main_Page()

      case _ =>
        println("Wrong Entry, Choose Another Option [a][b]: ")
        Run_Program.delay(2000)
        Publish_Iterable_Messages()
    }
  }
}

