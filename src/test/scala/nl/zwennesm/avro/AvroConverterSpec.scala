package nl.zwennesm.avro

import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.json4s.{Formats, NoTypeHints}
import org.scalatest.FlatSpec

import scala.util.Success

class AvroConverterSpec extends FlatSpec {

  private val converter = new AvroConverter()
  private implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)

  "case class toJson parsing" should "return string" in {
    val test = new Test(1, "hello")
    val json = write(test)
    assert(json == converter.toJson(test))
  }

  "case class fromJson parsing" should "return Success(type)" in {
    val test = new Test(1, "world")
    val json = write(test)
    assert(Success(test) == converter.fromJson(json))
  }

}
