package nl.zwennesm.core

import com.typesafe.config.ConfigFactory

case class CustomConfig(
  appName: String,
  streamName: String,
  interval: Int,
  region: String,
  accessKey: String,
  secretKey: String,
)

object CustomConfig {
  def apply(): CustomConfig = {
    val raw = ConfigFactory.load()
    CustomConfig(
      appName = raw.getString("app-name"),
      streamName = raw.getString("kinesis.stream-name"),
      interval = raw.getInt("kinesis.batch-interval"),
      region = raw.getString("aws.region"),
      accessKey = raw.getString("aws.access-key"),
      secretKey = raw.getString("aws.secret-key")
    )
  }
}
