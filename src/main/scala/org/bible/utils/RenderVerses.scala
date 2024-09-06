package org.bible.utils

import org.bible.handlefiles.Schemas._

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

}
