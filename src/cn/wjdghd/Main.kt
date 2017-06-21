package cn.wjdghd

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager

class Main : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val application = ApplicationManager.getApplication()
        val myComponent = application.getComponent(My::class.java)
        myComponent.todo(e)
    }
}
