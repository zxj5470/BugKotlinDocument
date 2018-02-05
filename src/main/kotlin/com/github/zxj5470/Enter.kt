package com.github.zxj5470

import cn.wjdghd.*
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile

class BKDTypedHandler : EnterHandlerDelegate {
	var ok = false

	override fun postProcessEnter(psiFile: PsiFile, editor: Editor, context: DataContext): EnterHandlerDelegate.Result {
		var ret = EnterHandlerDelegate.Result.Continue
		println(ok)
		if (ok) {
			val document = editor.document
			val thisLine = getThisLine(editor)
			val realNextLine = getRealNextLine(editor)
			val realNext = getRealNext(editor)
			val stringFac = stringFactory(thisLine, realNextLine, realNext)
			val replaceString = document.text.replace(realNext, stringFac)
			ApplicationManager.getApplication().runWriteAction {
				CommandProcessor.getInstance().runUndoTransparentAction {
					document.setText(replaceString)
				}
			}
			println(replaceString)
			ret = EnterHandlerDelegate.Result.Stop
		}
		return ret
	}

	override fun preprocessEnter(p0: PsiFile, editor: Editor, p2: Ref<Int>, p3: Ref<Int>, p4: DataContext, p5: EditorActionHandler?): EnterHandlerDelegate.Result {
		ok = getThisLine(editor).endsWith("/**")
		return EnterHandlerDelegate.Result.Continue
	}
}