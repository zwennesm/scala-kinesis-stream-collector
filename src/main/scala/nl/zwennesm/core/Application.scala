package nl.zwennesm.core

import java.net.InetAddress

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker
import com.typesafe.scalalogging.LazyLogging
import nl.zwennesm.avro.AvroConverter
import nl.zwennesm.kinesis.{AmazonConfiguration, RecordProcessorFactory}
import nl.zwennesm.storage.RawFileStorage

object Application extends AmazonConfiguration with LazyLogging {

  //todo: move these to configurable object
  private val storage = new RawFileStorage()
  private val converter = new AvroConverter()

  def main(args: Array[String]): Unit = {
    val config = CustomConfig()
    val worker = createWorker(config)
    worker.run()
  }

  private def createWorker(config: CustomConfig): Worker = {
    val hostname = InetAddress.getLocalHost.toString
    val kinesisConfig = createKinesisConfig(config, hostname)
    val processor = new RecordProcessorFactory(converter, storage)

    new Worker.Builder()
      .config(kinesisConfig)
      .recordProcessorFactory(processor)
      .build()
  }

}
