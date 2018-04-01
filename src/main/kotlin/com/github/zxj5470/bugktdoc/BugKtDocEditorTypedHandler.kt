package com.github.zxj5470.bugktdoc

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class BugKtDocEditorTypedHandler : TypedHandlerDelegate() {

	override fun charTyped(c: Char, project: Project?, editor: Editor, file: PsiFile): Result {
		if (isAlwaysShow && c == '*' && getCurrentLine(editor).endsWith("/**")) {
			Notifications.Bus.notify(Notification("com.github.zxj5470.bugktdoc.notification",
				BugKtDocBundle.message("bugktdoc.notation.title"),
				BugKtDocBundle.message("bugktdoc.notation.content"),
				NotificationType.INFORMATION))
		}
		return super.charTyped(c, project, editor, file)
	}
}