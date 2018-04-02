package com.github.zxj5470.bugktdoc

import cn.wjdghd.entity.newBeginIndents
import com.github.zxj5470.bugktdoc.parser.BugKtDocParser
import com.github.zxj5470.bugktdoc.parser.generator.BugKtDocGenerator
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocModel
import com.github.zxj5470.bugktdoc.parser.psi.TempEmpty
import com.github.zxj5470.bugktdoc.util.getCurrentLine
import com.github.zxj5470.bugktdoc.util.getNextLine
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
	var result: BugKtDocModel = TempEmpty()

	/**
	 *  after. so the `currentLine` is different from [preprocessEnter]
	 */
	override fun postProcessEnter(psiFile: PsiFile, editor: Editor, context: DataContext): EnterHandlerDelegate.Result {
		if (pluginActive && canGenerateDocument) {
			if (result is TempEmpty) return EnterHandlerDelegate.Result.Continue
			editor.run {
				val offset = caretModel.currentCaret.offset
				val indentString = getCurrentLine(editor).newBeginIndents()
				ApplicationManager.getApplication().runWriteAction {
					CommandProcessor.getInstance().runUndoTransparentAction {
						document.insertString(offset, BugKtDocGenerator.generate(result, indentString))
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
		result = BugKtDocParser.parse(editor)
		return EnterHandlerDelegate.Result.Continue
	}
}