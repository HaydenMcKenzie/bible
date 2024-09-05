package org.bible.verses

import scala.io.Source

case class Verse(book: String, chapter: Int, sectionTitle: String, number: Int, verse: String, isNewParagraph: Boolean)

object bibleVerses {
  def main(args: Array[String]): Unit = {
    val filename = "data/verses.csv"
    val verses = readCSV(filename)

    // Example usage
    val query0 = "Genesis 1-2:17"
    val query1 = "Genesis 1-2"
    val query2 = "Genesis 1:15-2:17"
    val query3 = "Genesis 1"

    println("\nGenesis 1-2:17 Test\n")
    println(renderVerses(searchVerses(verses, query0)))
    println("\nGenesis 1-2 Test\n")
    println(renderVerses(searchVerses(verses, query1)))
    println("\nGenesis 1:15-2:17 Test\n")
    println(renderVerses(searchVerses(verses, query2)))
    println("\nGenesis 1 Test\n")
    println(renderVerses(searchVerses(verses, query3)))
  }

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

  def searchVerses(verses: List[Verse], query: String): List[Verse] = {
    val chapterPattern = """Genesis (\d+)$""".r
    val rangePattern = """Genesis (\d+)(?::(\d+))?-(\d+)(?::(\d+))?$""".r

    query match {
      // Single chapter
      case chapterPattern(chapter) =>
        verses.filter(_.chapter == chapter.toInt)

      // Range of verses possibly across chapters
      case rangePattern(startChapter, startVerse, endChapter, endVerse) =>
        verses.filter { v =>
          val sc = startChapter.toInt
          val ec = endChapter.toInt
          val sv = Option(startVerse).map(_.toInt).getOrElse(1)
          val ev = Option(endVerse).map(_.toInt).getOrElse(Int.MaxValue)

          (v.chapter > sc && v.chapter < ec) ||
            (v.chapter == sc && v.number >= sv) ||
            (v.chapter == ec && v.number <= ev) ||
            (v.chapter == sc && ec == sc && v.number >= sv && v.number <= ev)
        }

      // Invalid query
      case _ =>
        println(s"Invalid query: $query")
        Nil
    }
  }

  def renderVerses(verses: List[Verse]): String = {
    val result = new StringBuilder
    var lastTitle = ""

    verses.foreach { verse =>
      // If the section title changes, append the new title
      if (verse.sectionTitle != lastTitle) {
        if (result.nonEmpty) {
          result.append("\n\n") // For paragraph break
        }
        result.append(s"${verse.sectionTitle}\n\n")
        lastTitle = verse.sectionTitle
      }
      if (verse.isNewParagraph && result.nonEmpty) {
        result.append("\n\n") // For paragraph break
      }
      // Append the verse number and then the verse text, removing double quotes
      result.append(s"${verse.number} ${verse.verse.replace("\"", "")} ")
    }

    // Return the accumulated verses as a single string
    result.toString().trim
  }
}