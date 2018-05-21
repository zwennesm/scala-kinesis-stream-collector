package nl.zwennesm.storage
import java.io.File

import com.sksamuel.avro4s._
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class RawFileStorage extends Storage {

  override def write[T: SchemaFor : ToRecord](data: T, pathName: String): Unit = {
    val os = AvroOutputStream.data[T](new File(pathName))
    os.write(data)
    os.flush()
    os.close()
  }

  override def write[T: SchemaFor : ToRecord](data: Seq[T], pathName: String): Unit =
    throw new NotImplementedException()

  override def read[T: SchemaFor : FromRecord](pathName: String): Set[T] = {
    val is = AvroInputStream.data[T](new File(pathName))
    val contents = is.iterator.toSet
    is.close()
    contents
  }
}
