package cn.wjdghd

import com.github.zxj5470.bugktdoc.*
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.util.containers.Stack

/**
 * @since 0.1.0
 */
class MainComponent : ApplicationComponent {
	override fun initComponent() {}
	override fun disposeComponent() {}
	override fun getComponentName(): String {
		return "BugKotlinDocument"
	}

	/**
	 * @param event AnActionEvent :
	 */
	fun todo(event: AnActionEvent) {
		//get this editor
		val editor = event.getData(PlatformDataKeys.EDITOR) ?: return
		editor.run {
			val offset = caretModel.currentCaret.offset
			val indentString = getTextAfter(editor, 1)
			val stringFac = genDocString(getFunctionNextLine(editor), indentString, true)
			ApplicationManager.getApplication().runWriteAction {
				CommandProcessor.getInstance().runUndoTransparentAction {
					document.insertString(offset, stringFac)
				}
			}
		}
	}
}
