package org.bible.utils

import org.bible.handlefiles.Schemas._


object SearchVerses {

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

}
