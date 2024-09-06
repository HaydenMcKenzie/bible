package org.bible.verses

import org.json4s._
import org.json4s.native.JsonMethods._

object play extends App {
  case class VerseJSON(verseNumber: Int, text: String, isNewParagraph: Boolean)
  case class Chapter(chapterNumber: Int, chapterTitle: String, verses: List[VerseJSON])
  case class Bible(book: String, chapters: List[Chapter])

  // Update readJSON to handle the new structure
  def readJSON(filename: String): Bible = {
    implicit val formats: Formats = DefaultFormats
    val source = scala.io.Source.fromFile(filename)
    val json = parse(source.mkString)
    source.close()

    val bibleJson = (json \ "genesisBook").extract[Bible] // Extract the "genesisBook" field as Bible
    bibleJson
  }

  def renderVersesJSON(versesWithTitles: List[(Int, String, VerseJSON)]): String = {
    val result = new StringBuilder
    var currentChapterTitle: String = ""
    var currentChapterNumber: Int = -1

    versesWithTitles.foreach { case (chapterNumber, chapterTitle, verse) =>
      if (chapterTitle != currentChapterTitle) {
        if (result.nonEmpty) result.append("\n\n") // Separate chapters
        result.append(s"$chapterTitle\n") // Append chapter title
        currentChapterTitle = chapterTitle
        currentChapterNumber = chapterNumber
      }

      if (verse.isNewParagraph && result.nonEmpty) {
        result.append("\n\n") // For paragraph break
      }

      // Display the chapter number for verse 1, otherwise display verse number
      if (verse.verseNumber == 1) {
        result.append(s"$chapterNumber ${verse.text.replace("\"", "")} ")
      } else {
        result.append(s"${verse.verseNumber} ${verse.text.replace("\"", "")} ")
      }
    }

    result.toString().trim
  }

  def searchVersesJSON(bible: Bible, query: String): List[(Int, String, VerseJSON)] = {
    val chapterPattern = """Genesis (\d+)$""".r
    val rangePattern = """Genesis (\d+)(?::(\d+))?-(\d+)(?::(\d+))?$""".r

    val versesWithChapterInfo = for {
      chapter <- bible.chapters
      verse <- chapter.verses
    } yield (chapter.chapterNumber, chapter.chapterTitle, verse)

    query match {
      // Single chapter
      case chapterPattern(chapter) =>
        versesWithChapterInfo
          .filter { case (chNum, _, _) => chNum == chapter.toInt }
          .map { case (chNum, chTitle, verse) => (chNum, chTitle, verse) }

      // Range of verses across chapters
      case rangePattern(startChapter, startVerse, endChapter, endVerse) =>
        val sc = startChapter.toInt
        val ec = endChapter.toInt
        val sv = Option(startVerse).map(_.toInt).getOrElse(1)
        val ev = Option(endVerse).map(_.toInt).getOrElse(Int.MaxValue)

        versesWithChapterInfo
          .filter { case (chNum, _, verse) =>
            (chNum > sc && chNum < ec) ||
              (chNum == sc && verse.verseNumber >= sv) ||
              (chNum == ec && verse.verseNumber <= ev) ||
              (chNum == sc && sc == ec && verse.verseNumber >= sv && verse.verseNumber <= ev)
          }
          .map { case (chNum, chTitle, verse) => (chNum, chTitle, verse) }

      // Invalid query
      case _ =>
        println(s"Invalid query: $query")
        Nil
    }
  }

  val filename = "data/genesisBook.json"
  val bible = readJSON(filename)

  // Example usage
  val query1 = "Genesis 1"
  val query2 = "Genesis 1:15"
  val query3 = "Genesis 1-3"
  val query4 = "Genesis 1-2:17"

  println("\"Genesis 1\"")
  println(renderVersesJSON(searchVersesJSON(bible, query1)))
  println("\"Genesis 1:15\"")
  println(renderVersesJSON(searchVersesJSON(bible, query2)))
  println("\"Genesis 1-3\"")
  println(renderVersesJSON(searchVersesJSON(bible, query3)))
  println("\"Genesis 1-2:17\"")
  println(renderVersesJSON(searchVersesJSON(bible, query4)))
}
