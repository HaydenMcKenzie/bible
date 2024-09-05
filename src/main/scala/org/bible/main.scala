package org.bible

import org.bible.handlefiles.ReadFiles.readCSV
import org.bible.utils.RenderVerses.renderVerses
import org.bible.utils.SearchVerses.searchVerses

object main extends App {

  val filename = "data/verses.csv"
  val verses = readCSV(filename)

  // Example usage
  val query3 = "Genesis 1-3"
  println(renderVerses(searchVerses(verses, query3)))
}
