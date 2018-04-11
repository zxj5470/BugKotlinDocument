package com.github.zxj5470.bugktdoc

import com.github.zxj5470.bugktdoc.util.getCurrentLineToCurrentChar
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class BugKtDocEditorTypedHandler : TypedHandlerDelegate() {
	override fun charTyped(c: Char, project: Project?, editor: Editor, file: PsiFile): Result {
		// It seems that CLion has no `org/jetbrains/kotlin/psi/KtFile`
		if (file.language.displayName != "Kotlin") return super.charTyped(c, project, editor, file)
		if (isTheFirstTime && c == '*' && getCurrentLineToCurrentChar(editor).endsWith("/**")) {
			Notifications.Bus.notify(Notification("com.github.zxj5470.bugktdoc.notification",
				BugKtDocBundle.message("bugktdoc.notation.title"),
				BugKtDocBundle.message("bugktdoc.notation.content"),
				NotificationType.INFORMATION))
			globalSettings.theFirstTile = false
		}
		return super.charTyped(c, project, editor, file)
	}
}