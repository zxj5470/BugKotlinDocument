package com.github.zxj5470.bugktdoc

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile

class BugKotlinEnterHandlerDelegate : EnterHandlerDelegate {

	var canGenerateDocument = false

	fun asda(): String??????????? {
		return ""
	}

	override fun postProcessEnter(psiFile: PsiFile, editor: Editor, context: DataContext): EnterHandlerDelegate.Result {
		if (canGenerateDocument) {
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

	override fun preprocessEnter(file: PsiFile, editor: Editor, caretOffset: Ref<Int>, caretAdvance: Ref<Int>, dataContext: DataContext, originalHandler: EditorActionHandler?): EnterHandlerDelegate.Result {
		canGenerateDocument = getCurrentLine(editor).endsWith("/**") && !editorNextLine(editor).trim().startsWith("*")
		return EnterHandlerDelegate.Result.Continue
	}
}