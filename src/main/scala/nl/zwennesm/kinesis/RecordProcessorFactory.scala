package nl.zwennesm.kinesis

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory
import nl.zwennesm.avro.Converter
import nl.zwennesm.storage.Storage

class RecordProcessorFactory(converter: Converter, storage: Storage) extends IRecordProcessorFactory {
  override def createProcessor() = new RecordProcessor(converter, storage)
}