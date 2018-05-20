package nl.zwennesm.kinesis

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory
import nl.zwennesm.core.CustomConfig

class RecordProcessorFactory(config: CustomConfig) extends IRecordProcessorFactory {
    override def createProcessor() = new RecordProcessor(config: CustomConfig)
}