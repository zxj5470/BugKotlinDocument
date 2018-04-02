package com.github.zxj5470.bugktdoc

import cn.wjdghd.entity.beginIndents
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile

class BugBtDocEnterHandlerDelegate : EnterHandlerDelegate {

	var canGenerateDocument = false

	// Temp solution. FIXME
	var indentString = ""

	/**
	 *  after. so the `currentLine` is different from [preprocessEnter]
	 */
	override fun postProcessEnter(psiFile: PsiFile, editor: Editor, context: DataContext): EnterHandlerDelegate.Result {
		if (pluginActive && canGenerateDocument) {
			editor.run {
				val offset = caretModel.currentCaret.offset
				val stringFac = genDocString(getFunctionNextLine(editor), indentString)
				ApplicationManager.getApplication().runWriteAction {
					CommandProcessor.getInstance().runUndoTransparentAction {
						document.insertString(offset, stringFac)
					}
				}
			}
		}
		return EnterHandlerDelegate.Result.Continue
	}

	// when pressing (before invoke)
	override fun preprocessEnter(file: PsiFile, editor: Editor, caretOffset: Ref<Int>, caretAdvance: Ref<Int>, dataContext: DataContext, originalHandler: EditorActionHandler?): EnterHandlerDelegate.Result {
		if (!pluginActive)
			return EnterHandlerDelegate.Result.Continue

		canGenerateDocument = getCurrentLine(editor).endsWith("/**") && !getNextLine(editor).trim().startsWith("*")
		if (canGenerateDocument) indentString = getTextAfter(editor, 1).beginIndents()
		return EnterHandlerDelegate.Result.Continue
	}
}