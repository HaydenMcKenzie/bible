package org.bible.handlefiles

object Schemas {

  case class Verse(book: String, chapter: Int, sectionTitle: String, number: Int, verse: String, isNewParagraph: Boolean)

}
