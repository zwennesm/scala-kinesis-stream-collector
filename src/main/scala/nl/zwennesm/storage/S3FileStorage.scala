package nl.zwennesm.storage
import awscala.Region
import awscala.s3.{S3, S3Object}
import com.sksamuel.avro4s.{FromRecord, SchemaFor, ToRecord}
import com.typesafe.scalalogging.LazyLogging
import nl.zwennesm.core.CustomConfig

class S3FileStorage(config: CustomConfig) extends Storage with LazyLogging {

  implicit val region: Region = Region.apply(config.region)
  implicit val s3: S3 = S3(accessKeyId = config.accessKey, secretAccessKey = config.secretKey)

  private val storage = new RawFileStorage()

  private def sendToS3(pathName: String): Unit = {
      s3.bucket(config.bucketName) match {
      case Some(b) => b.put(pathName, new java.io.File(pathName))
      case None => logger.error(s"cannot put file to s3; file does not exist: $pathName")
    }
  }

  private def readFromS3(keyName: String): String = {
    val file = s3.bucket(config.bucketName) match {
      case Some(b) => b.get(keyName)
      case None => logger.error(s"bucket with name ${config.bucketName} does not exist")
    }

    file match {
      case Some(f) => scala.io.Source.fromInputStream(f.contents).mkString
      case None =>
        logger.error(s"file with path: $keyName not found on S3")
        String.valueOf(None)
    }
  }

  override def write[T: SchemaFor : ToRecord](data: T, pathName: String): Unit = {
    storage.write(data, pathName)
    sendToS3(pathName)
  }

  override def write[T: SchemaFor : ToRecord](data: Seq[T], pathName: String): Unit = {
    storage.write(data, pathName)
    sendToS3(pathName)
  }

  override def read[T: SchemaFor : FromRecord](pathName: String): Set[T] =
    throw new NotImplementedError()

}
