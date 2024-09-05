package org.bible

import org.json4s._
import org.json4s.native.JsonMethods._

object play extends App {

  implicit val formats = DefaultFormats

  case class Person(name: String, age: Int)

  val jsonString = """{ "name": "John", "age": 25 }"""
  val json = parse(jsonString)
  val person = json.extract[Person]
  println(person.name) // Output: John

}
