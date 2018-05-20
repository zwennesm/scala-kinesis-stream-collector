package nl.zwennesm.core

import java.net.InetAddress

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker
import com.typesafe.scalalogging.LazyLogging
import nl.zwennesm.kinesis.{AmazonConfiguration, RecordProcessorFactory}

object Application extends AmazonConfiguration with LazyLogging {

  def main(args: Array[String]): Unit = {
    val config = CustomConfig()
    val worker = createWorker(config)
    worker.run()
  }

  private def createWorker(config: CustomConfig): Worker = {
    val hostname = InetAddress.getLocalHost.toString
    val kinesisConfig = createKinesisConfig(config, hostname)
    val processor = new RecordProcessorFactory(config)

    new Worker.Builder()
      .config(kinesisConfig)
      .recordProcessorFactory(processor)
      .build()
  }

}
