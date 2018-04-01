package cn.wjdghd

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager

/**
 * @since 0.1.0
 */
class Main : AnAction() {
	override fun actionPerformed(e: AnActionEvent) {
		val application = ApplicationManager.getApplication()
		val myComponent = application.getComponent(MainComponent::class.java)
		myComponent.todo(e)
	}
}
