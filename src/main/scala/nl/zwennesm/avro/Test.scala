package nl.zwennesm.avro

import com.sksamuel.avro4s.SchemaFor

case class Test(id: Int, name: String)

object Test {
  implicit val schema: SchemaFor[Test] = SchemaFor[Test]
}