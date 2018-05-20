package nl.zwennesm.kinesis

import com.amazonaws.auth.{AWSCredentials, AWSCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.{InitialPositionInStream, KinesisClientLibConfiguration}
import nl.zwennesm.core.CustomConfig

trait AmazonConfiguration {

  private def getCredentials(cfg: CustomConfig): AWSCredentialsProvider = {
    new AWSCredentialsProvider {
      override def getCredentials: AWSCredentials =
        new BasicAWSCredentials(cfg.accessKey, cfg.secretKey)
      override def refresh(): Unit = {}
    }
  }

  def createKinesisConfig(cfg: CustomConfig, workerId: String): KinesisClientLibConfiguration = {
    val credentials = getCredentials(cfg)
    new KinesisClientLibConfiguration(cfg.appName, cfg.streamName, credentials, workerId)
      .withRegionName(cfg.region)
      .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON)
  }

}
