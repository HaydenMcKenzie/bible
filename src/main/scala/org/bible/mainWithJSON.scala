package org.bible

import org.bible.handlefiles.ReadFiles.readJSON
import org.bible.utils.RenderVerses.renderVersesJSON
import org.bible.utils.SearchVerses.searchVersesJSON

object mainWithJSON extends App {

  val filename = "data/bible.json"
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
