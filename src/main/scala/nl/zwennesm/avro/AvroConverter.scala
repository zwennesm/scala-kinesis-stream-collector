package nl.zwennesm.avro
import java.io.{ByteArrayOutputStream, File}

import com.sksamuel.avro4s._
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream

import scala.util.Try

class AvroConverter extends Converter {
  override def toJson[T: SchemaFor : ToRecord](obj: T): String = {
    val out = new ByteArrayOutputStream()
    val output = AvroOutputStream.json[T](out)
    output.write(obj)
    output.close()
    out.toString("UTF-8")
  }

  override def fromJson[T: SchemaFor : FromRecord](json: String): Try[T] = {
    val in = new ByteInputStream(json.getBytes("UTF-8"), json.length)
    val input = AvroInputStream.json[T](in)
    input.singleEntity
  }

  override def writeToFile[T: SchemaFor : ToRecord](data: T, pathName: String): Unit = {
    val os = AvroOutputStream.data[T](new File(pathName))
    os.write(data)
    os.flush()
    os.close()
  }

  override def readFromFile[T: SchemaFor : FromRecord](pathName: String): Set[T] = {
    val is = AvroInputStream.data[T](new File(pathName))
    val contents = is.iterator.toSet
    is.close()
    contents
  }
}
