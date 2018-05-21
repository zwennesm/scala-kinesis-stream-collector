package nl.zwennesm.storage
import java.io.File

import com.sksamuel.avro4s._

class RawFileStorage extends Storage {

  /* max amount of files to be written to a file */
  private val MAX_ROWS = 1000

  override def write[T: SchemaFor : ToRecord](data: T, pathName: String): Unit = {
    val os = AvroOutputStream.data[T](new File(pathName))
    os.write(data)
    os.flush()
    os.close()
  }

  override def write[T: SchemaFor : ToRecord](data: Seq[T], pathName: String): Unit = {

  }

  override def read[T: SchemaFor : FromRecord](pathName: String): Set[T] = {
    val is = AvroInputStream.data[T](new File(pathName))
    val contents = is.iterator.toSet
    is.close()
    contents
  }
}
