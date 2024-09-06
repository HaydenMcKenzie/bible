package org.bible

import org.bible.handlefiles.ReadFiles.readJSON
import org.bible.utils.RenderVerses.renderVersesJSON
import org.bible.utils.SearchVerses.searchVersesJSON

object mainWithJSON extends App {

  val filename = "data/genesisBook.json"
  val bible = readJSON(filename)

  // Example usage
  val query1 = "Genesis 1-3"
  println(renderVersesJSON(searchVersesJSON(bible, query1)))
}
