package nl.zwennesm.avro

import java.nio.file.{Files, Path, Paths}

import com.sksamuel.avro4s.SchemaFor
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.json4s.{Formats, NoTypeHints}
import org.scalatest.FlatSpec

import scala.util.Success

class AvroConverterSpec extends FlatSpec {

  case class Test(id: Int, name: String)

  object Test {
    implicit val schema: SchemaFor[Test] = SchemaFor[Test]
  }

  private val converter = new AvroConverter()
  private implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)

  "case class toJson parsing" should "return string" in {
    val test = new Test(1, "hello")
    val json = write(test)
    assert(json == converter.toJson[Test](test))
  }

  "case class fromJson parsing" should "return Success(type)" in {
    val test = new Test(1, "world")
    val json = write(test)
    assert(Success(test) == converter.fromJson[Test](json))
  }

  "case class writeToFile" should "create a non-empty file" in {
    val test = new Test(1, "writing")
    converter.writeToFile[Test](test, "test.avro")
    assert(Files.exists(Paths.get("test.avro")))
  }

  "case class readFromFile" should "not be empty or null" in {
    val test = new Test(1, "writing")
    val path = "test.avro"
    converter.writeToFile[Test](test, path)
    val data = converter.readFromFile[Test](path)
    assert(data == Set(test))
    Files.delete(Paths.get(path))
  }
}
