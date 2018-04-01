package com.github.zxj5470.bugktdoc

import cn.wjdghd.*
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile

class BugKotlinEnterHandlerDelegate : EnterHandlerDelegate {
	var ok = false

	override fun postProcessEnter(psiFile: PsiFile, editor: Editor, context: DataContext): EnterHandlerDelegate.Result {
		if (ok) {
			val document = editor.document
			val offset = editor.caretModel.currentCaret.offset
			val stringFac = genDocString(getFunctionNextLine(editor))
			ApplicationManager.getApplication().runWriteAction {
				CommandProcessor.getInstance().runUndoTransparentAction {
					document.insertString(offset, stringFac)
				}
			}
		}
		return EnterHandlerDelegate.Result.Continue
	}


	override fun preprocessEnter(p0: PsiFile, editor: Editor, p2: Ref<Int>, p3: Ref<Int>, p4: DataContext, p5: EditorActionHandler?): EnterHandlerDelegate.Result {
		ok = getCurrentLine(editor).endsWith("/**") && !editorNextLine(editor).trim().startsWith("*")
		return EnterHandlerDelegate.Result.Continue
	}
}