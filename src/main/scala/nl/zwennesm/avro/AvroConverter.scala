package nl.zwennesm.avro
import java.io.ByteArrayOutputStream

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

}
