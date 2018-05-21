package nl.zwennesm.storage

import java.nio.file.{Files, Paths}

import nl.zwennesm.Test
import org.scalatest.FlatSpec

class StorageSpec extends FlatSpec {

  private val storage = new RawFileStorage()

  "case class writeToFile" should "create a non-empty file" in {
    val test = new Test(1, "writing")
    storage.write[Test](test, "test.avro")
    assert(Files.exists(Paths.get("test.avro")))
  }

  "case class readFromFile" should "not be empty or null" in {
    val test = new Test(1, "writing")
    val path = "test.avro"
    storage.write[Test](test, path)
    val data = storage.read[Test](path)
    assert(data == Set(test))
    Files.delete(Paths.get(path))
  }
}
