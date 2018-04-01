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

fun getFunctionDeclarationLine(str: String): String {
	val charStack = Stack<Char>()
	val index = str.indexOf('(')
	val s = str.substring(index)
	var top: Char
	var indexEnd = 0
	for (i in s.indices) {
		top = if (charStack.empty()) ' ' else charStack.peek()
		when (s[i]) {
			'\'' -> {
				if (top != '\'') charStack.push(s[i])
				else charStack.pop()
			}
			'\"' -> {
				if (top != '\"') charStack.push(s[i])
				else charStack.pop()
			}
			'(', '{', '<' -> charStack.push(s[i])
			')' -> if (top == '(') charStack.pop()
			'}' -> if (top == '{') charStack.pop()
			'>' -> if (top == '<') charStack.pop()
		}
		if (charStack.isEmpty()) {
			if (s[i] == ')') {
				indexEnd = i
				break
			}
		}
	}
	val functionHead = s.substring(1, indexEnd)
	return functionHead
}