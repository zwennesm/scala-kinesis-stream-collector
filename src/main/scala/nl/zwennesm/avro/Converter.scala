package nl.zwennesm.avro

import com.sksamuel.avro4s.{FromRecord, SchemaFor, ToRecord}

import scala.util.Try

trait Converter {
  def toJson[T: SchemaFor : ToRecord](obj: T): String
  def fromJson[T: SchemaFor : FromRecord](data: String): Try[T]
  def writeToFile[T: SchemaFor : ToRecord](data: T, pathName: String): Unit
  def readFromFile[T: SchemaFor : FromRecord](pathName: String): Set[T]
}
