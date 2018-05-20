package nl.zwennesm.avro

import scala.util.Try

trait Converter[Test] {
  def toJson(obj: Test): String
  def fromJson(data: String): Try[Test]
}
