package nl.zwennesm.storage

import com.sksamuel.avro4s.{FromRecord, SchemaFor, ToRecord}

trait Storage {
  def write[T: SchemaFor : ToRecord](data: T, pathName: String): Unit
  def write[T: SchemaFor : ToRecord](data: Seq[T], pathName: String): Unit
  def read[T: SchemaFor : FromRecord](pathName: String): Set[T]
}
