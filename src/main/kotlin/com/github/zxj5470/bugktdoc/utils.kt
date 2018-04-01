package com.github.zxj5470.bugktdoc

import cn.wjdghd.entity.splitWithParams
import cn.wjdghd.getFunctionDeclarationLine
import com.github.zxj5470.bugktdoc.constants.*
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.util.containers.Stack

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

fun getFunctionNextLine(editor: Editor): String {
	val document = editor.document
	val caretModel = editor.caretModel
	val caretOffset = caretModel.offset
	val lineNum = document.getLineNumber(caretOffset) + 1
	val lineStartOffset = document.getLineStartOffset(lineNum)
	val s = document.text.substring(lineStartOffset)
	val charStack = Stack<Char>()
	var top: Char
	var indexEnd: Int = 0
	for (i in s.indices) {
		top = if (charStack.empty()) ' ' else charStack.peek()
		when (s[i]) {
			'\'' -> {
				if (top != '\'') charStack.push(s[i])
				else charStack.pop()
			}
			'\"' -> {
				if (top != '\"') charStack.push(s[i])
				else charStack.pop()
			}
			'(', '{', '<' -> charStack.push(s[i])
			')' -> if (top == '(') charStack.pop()
			'}' -> if (top == '{') charStack.pop()
			'>' -> if (top == '<') charStack.pop()
		}
		if (charStack.isEmpty()) {
			if (s[i] == '}') {
				indexEnd = i + 1
				break
			}
		}
	}
	val functionHead = s.substring(1, indexEnd)
	return functionHead
}