package com.github.zxj5470.bugktdoc.util

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange


/**
 * @author zxj5470
 * @date 2018/4/1
 * @since 0.2.0
 */
fun getCurrentLineToCurrentChar(editor: Editor): String =
	editor.run {
		val offset = this.caretModel.offset
		lineNumber(0).run {
			return if (this > document.lineCount) ""
			else document.let {
				val lineStartOffset = it.getLineStartOffset(this)
				it.getText(TextRange(lineStartOffset, offset))
			}
		}
	}

private fun getLine(editor: Editor, afterCurrentLine: Int = 0): String {
	editor.run {
		lineNumber(afterCurrentLine).run {
			return if (this > document.lineCount) ""
			else document.let {
				val lineStartOffset = it.getLineStartOffset(this)
				val lineEndOffset = it.getLineEndOffset(this)
				it.getText(TextRange(lineStartOffset, lineEndOffset))
			}
		}
	}
}


/**
 * @param editor Editor :
 * @param afterCurrentLine Int : default is 0
 * @see [getLine]
 * @author zxj5470
 */
fun getTextAfterLine(editor: Editor, afterCurrentLine: Int = 0): String {
	editor.run {
		val lineNum = lineNumber(afterCurrentLine)
		if (lineNum > document.lineCount) return ""
		val lineStartOffset = document.getLineStartOffset(lineNum)
		return document.text.substring(lineStartOffset)
	}
}

fun getTextAfter(editor: Editor): String =
	editor.run {
		document.text.substring(caretModel.offset)
	}

private fun Editor.lineNumber(afterCurrentLine: Int): Int {
	val caretOffset = caretModel.offset
	return document.getLineNumber(caretOffset) + afterCurrentLine
}
