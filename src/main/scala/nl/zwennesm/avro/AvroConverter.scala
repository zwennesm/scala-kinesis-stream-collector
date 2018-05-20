package nl.zwennesm.avro
import java.io.ByteArrayOutputStream

import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream, SchemaFor}
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream

import scala.util.Try

class AvroConverter extends Converter[Test] {
  override def toJson(obj: Test): String = {
    val out = new ByteArrayOutputStream()
    val output = AvroOutputStream.json[Test](out)
    output.write(obj)
    output.close()
    out.toString("UTF-8")
  }

  override def fromJson(json: String): Try[Test] = {
    val in = new ByteInputStream(json.getBytes("UTF-8"), json.length)
    val input = AvroInputStream.json[Test](in)
    input.singleEntity
  }
}
