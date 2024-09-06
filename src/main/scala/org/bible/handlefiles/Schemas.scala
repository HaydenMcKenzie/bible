package org.bible.handlefiles

object Schemas {

  case class Verse(book: String, chapter: Int, sectionTitle: String, number: Int, verse: String, isNewParagraph: Boolean)


  case class VerseJSON(verseNumber: Int, text: String, isNewParagraph: Boolean)
  case class Chapter(chapterNumber: Int, chapterTitle: String, verses: List[VerseJSON])
  case class Bible(book: String, chapters: List[Chapter])
  case class BibleWrapper(bible: Bible)
}
