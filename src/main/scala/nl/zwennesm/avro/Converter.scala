package nl.zwennesm.avro

import com.sksamuel.avro4s.{FromRecord, SchemaFor, ToRecord}

import scala.util.Try

trait Converter {
  def toJson[T: SchemaFor : ToRecord](obj: T): String
  def fromJson[T: SchemaFor : FromRecord](data: String): Try[T]
}
