package org.bible.handlefiles

import org.bible.handlefiles.Schemas.Verse
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

}
