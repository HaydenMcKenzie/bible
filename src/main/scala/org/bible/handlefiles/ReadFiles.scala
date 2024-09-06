package org.bible.handlefiles

import org.bible.handlefiles.Schemas.{Bible, Verse}
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.io.Source

object ReadFiles {

  def readCSV(filename: String): List[Verse] = {
    val source = Source.fromFile(filename)
    val verses = source.getLines().drop(1).map { line =>
      val parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

      val book = parts(0)
      val chapter = parts(1).toInt
      val sectionTitle = parts(2)
      val number = parts(3).toInt
      val verse = parts(4)
      val isNewParagraph = parts(5).toBoolean

      Verse(book, chapter, sectionTitle, number, verse, isNewParagraph)
    }.toList
    source.close()
    verses
  }

  def readJSON(filename: String): Bible = {
    implicit val formats: Formats = DefaultFormats
    val source = scala.io.Source.fromFile(filename)
    val json = parse(source.mkString)
    source.close()

    val bibleJson = (json \ "genesisBook").extract[Bible] // Extract the "genesisBook" field as Bible
    bibleJson
  }

}
