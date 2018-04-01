package com.github.zxj5470.bugktdoc

import cn.wjdghd.entity.splitWithParams
import cn.wjdghd.getFunctionDeclarationLine
import com.github.zxj5470.bugktdoc.constants.*
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange

/**
 * @author: zxj5470
 * @date: 2018/4/1
 */

fun editorNextLine(editor: Editor): String {
	val document = editor.document
	val caretModel = editor.caretModel
	val caretOffset = caretModel.offset
	val lineNum = document.getLineNumber(caretOffset) + 1
	val lineStartOffset = document.getLineStartOffset(lineNum)
	val lineEndOffset = document.getLineEndOffset(lineNum)
	return document.getText(TextRange(lineStartOffset, lineEndOffset))
}

fun getCurrentLine(editor: Editor): String {
	val document = editor.document
	val caretModel = editor.caretModel
	val caretOffset = caretModel.offset
	val lineNum = document.getLineNumber(caretOffset)
	val lineStartOffset = document.getLineStartOffset(lineNum)
	val lineEndOffset = document.getLineEndOffset(lineNum)
	return document.getText(TextRange(lineStartOffset, lineEndOffset))
}

fun genDocString(realNextLine: String): String = buildString {
	append(DocControl.LF)
	getFunctionDeclarationLine(realNextLine)
			.splitWithParams()
			.filter { it.isNotEmpty() }
			.forEachIndexed { index, it ->
				if (index >= 1) {
					append(DocControl.LF)
				}
				append(DocControl.INNER)
				append(DocDecoration.PARAM)
				append(DocControl.SPACE)
				append(it)
			}
}