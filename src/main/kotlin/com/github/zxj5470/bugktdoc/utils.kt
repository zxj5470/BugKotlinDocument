package com.github.zxj5470.bugktdoc

import cn.wjdghd.entity.splitWithParams
import cn.wjdghd.getFunctionDeclarationLine
import com.github.zxj5470.bugktdoc.constants.*
import com.intellij.CommonBundle
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.util.containers.Stack
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.*

/**
 * @author zxj5470
 * @date 2018/4/1
 */

/**
 * @since 0.2.0
 */
fun editorNextLine(editor: Editor): String = getLine(editor, 1)

private fun getLine(editor: Editor, afterCurrentLine: Int = 0): String {
	editor.run {
		val caretOffset = caretModel.offset
		val lineNum = document.getLineNumber(caretOffset) + afterCurrentLine
		if (lineNum > document.lineCount) return ""
		val lineStartOffset = document.getLineStartOffset(lineNum)
		val lineEndOffset = document.getLineEndOffset(lineNum)
		return document.getText(TextRange(lineStartOffset, lineEndOffset))
	}
}

fun getCurrentLine(editor: Editor): String = getLine(editor)

/**
 * @param editor Editor :
 * @param afterCurrentLine Int : default is 0
 * @see [getLine]
 * @author zxj5470
 */
fun getTextAfter(editor: Editor, afterCurrentLine: Int = 0): String {
	editor.run {
		val caretOffset = caretModel.offset
		val lineNum = document.getLineNumber(caretOffset) + afterCurrentLine
		if (lineNum > document.lineCount) return ""
		val lineStartOffset = document.getLineStartOffset(lineNum)
		return document.text.substring(lineStartOffset)
	}
}

/**
 * @since 0.2.0
 */
fun genDocString(realNextLine: String, indent: String = "", byAltN: Boolean = false): String = buildString {
	append(BugKtDocControl.LF) //	`LF` is:	/**   \n
	getFunctionDeclarationLine(realNextLine)
		.splitWithParams()
		.filter { it.isNotEmpty() }
		.forEachIndexed { index, it ->
			if (index >= 1) {
				append(BugKtDocControl.LF)
			}
			append(indent)
			append(BugKtDocControl.INNER)
			append(BugKtDocDecoration.PARAM)
			append(BugKtDocControl.SPACE)
			append(it)
			append(BugKtDocControl.TYPE_SPLIT_COLON)
		}
	if (byAltN) {
		append(BugKtDocControl.LF)
		append(indent)
		append(BugKtDocControl.END)
	}
}

/**
 * @since 0.2.0
 */
fun getFunctionNextLine(editor: Editor): String {
	val s = getTextAfter(editor, 1)
	val charStack = Stack<Char>()
	var top: Char
	var indexEnd = 0
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
	val functionHead = s.substring(0, indexEnd)
	return functionHead
}

val isAlwaysShow
	get() = true

/**
 * @ref https://github.com/ice1000/julia-intellij/blob/master/src/org/ice1000/julia/lang/julia-infos.kt
 * class [JuliaBundle]
 */
object BugKtDocBundle {
	@NonNls
	private const val BUNDLE = "BugKtDocBundle"
	private val bundle: ResourceBundle by lazy { ResourceBundle.getBundle(BUNDLE) }
	@JvmStatic
	fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
		CommonBundle.message(bundle, key, *params)
}