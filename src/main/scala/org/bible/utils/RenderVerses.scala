package org.bible.utils

import org.bible.handlefiles.Schemas.Verse

object RenderVerses {

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
