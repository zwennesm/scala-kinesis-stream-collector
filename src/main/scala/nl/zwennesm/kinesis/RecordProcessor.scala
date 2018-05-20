package nl.zwennesm.kinesis

import java.util

import com.amazonaws.services.kinesis.clientlibrary.interfaces.{IRecordProcessor, IRecordProcessorCheckpointer}
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason
import com.amazonaws.services.kinesis.model.Record
import com.snowplowanalytics.snowplow.analytics.scalasdk.json.EventTransformer
import com.typesafe.scalalogging.LazyLogging
import nl.zwennesm.core.CustomConfig

import scala.collection.JavaConverters._

class RecordProcessor(config: CustomConfig) extends IRecordProcessor with LazyLogging {

  override def initialize(shardId: String): Unit = {
    logger.info(s"initialized record processor with shard: $shardId")
  }

  override def processRecords(records: util.List[Record], checkpoint: IRecordProcessorCheckpointer): Unit = {
    logger.debug(s"processing ${records.size} records")
    val events = records.asScala
      .map(line => EventTransformer.transform(new String(line.getData.array(), "UTF-8")))
      .filter(_.isRight)
      .flatMap(_.right.toOption)

  }

  override def shutdown(checkpoint: IRecordProcessorCheckpointer, reason: ShutdownReason): Unit = {
    logger.info(s"shutting down processor with reason: $reason")
  }
}