package com.github.zxj5470

import cn.wjdghd.*
import cn.wjdghd.constants.RuntimeConstants.*
import cn.wjdghd.entity.beginSpaces
import cn.wjdghd.entity.splitWithParams
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile

class BKDTypedHandler : EnterHandlerDelegate {
	var ok = false

	override fun postProcessEnter(psiFile: PsiFile, editor: Editor, context: DataContext): EnterHandlerDelegate.Result {
		return if (ok) EnterHandlerDelegate.Result.Stop else EnterHandlerDelegate.Result.Default
	}


	override fun preprocessEnter(p0: PsiFile, editor: Editor, p2: Ref<Int>, p3: Ref<Int>, p4: DataContext, p5: EditorActionHandler?): EnterHandlerDelegate.Result {
		ok = getCurrentLine(editor).endsWith("/**")
		if (ok) {
			val document = editor.document
			val thisLine = getCurrentLine(editor)
			val realNextLine = getRealNextLine(editor)
			// avoid `*` multi-match
			if(realNextLine.trim().startsWith("*")){
				return EnterHandlerDelegate.Result.Default
			}
			val realNext = getRealNext(editor)
			val stringFac = stringFactory(thisLine, realNextLine, realNext)
//			println("---thisLine---\n" +
//					"$thisLine \n" +
//					"---realNextLine---\n" +
//					"$realNextLine \n" +
//					"---$realNext---\n" +
//					"$stringFac\n" +
//					"---END---")
			val replaceString = document.text.replace(realNext, stringFac)
			ApplicationManager.getApplication().runWriteAction {
				CommandProcessor.getInstance().runUndoTransparentAction {
					document.setText(replaceString)
				}
			}
			println(replaceString)
		}
		return if (ok) EnterHandlerDelegate.Result.Stop else EnterHandlerDelegate.Result.Default
	}
}

fun stringFactory(thisLine: String, realNextLine: String, realNext: String): String {
	val beginIndent = realNextLine.beginSpaces()
	val docLines = getFunctionDeclarationLine(realNextLine).splitWithParams()
	//`  /** ` in first line
	val sb = StringBuilder()
	sb.append(thisLine)
	sb.append(LF)
	// ` * ` in each line
	docLines.forEach {
		sb.append(beginIndent)
		sb.append(DOC_INNER)
		if (it.isNotEmpty()) {
			sb.append(PARAM)
			sb.append(SPACE)
			sb.append(it)
			sb.append(LINE_SPLIT_COLON)
		}
		sb.append(LF)
	}
	sb.append(beginIndent)
	sb.append(DOC_END)
	return realNext.replace(thisLine, sb.toString())
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