package cn.wjdghd

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.util.containers.Stack

class My : ApplicationComponent {
    lateinit var mEditor: Editor
    lateinit var document: Document
    lateinit var globalTExt: String
    val bus = ApplicationManager.getApplication().messageBus
    val connection = bus.connect()

    override fun initComponent() {
    }

    override fun disposeComponent() {}
    override fun getComponentName(): String {
        return "wjdghd"
    }

    fun todo(event: com.intellij.openapi.actionSystem.AnActionEvent) {
        mEditor = event.getData(com.intellij.openapi.actionSystem.PlatformDataKeys.EDITOR) ?: return
        val thisLine = getThisLine(mEditor)
        val nextLine = getNextLine(mEditor)
        println(thisLine)
        println(nextLine)
        if (thisLine.trim() == "/**") {
            document = mEditor.document
            globalTExt = document.text
            val replaceString = document.text.replaceFirst(thisLine, stringFactory(thisLine, nextLine))
            globalTExt = replaceString

            ApplicationManager.getApplication().runWriteAction {
                CommandProcessor.getInstance().runUndoTransparentAction {
                    this.document.setText(globalTExt)
                }
            }
        }

    }

    private fun getNextLine(editor: Editor): String {
        val document = editor.document
        val caretModel = editor.caretModel
        val caretOffset = caretModel.offset
        val lineNum = document.getLineNumber(caretOffset)
        val lineStartOffset = document.getLineStartOffset(lineNum + 1)
        val sub = document.text.substring(lineStartOffset)
        val charStack = Stack<Char>()

        val index = sub.indexOf('(')
        val s = sub.substring(index)
        var top: Char
        var indexEnd: Int = 0
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
                '(', '{' -> charStack.push(s[i])
                ')' -> if (top == '(') charStack.pop()
                '}' -> if (top == '}') charStack.pop()
            }
            if (charStack.isEmpty()) {
                indexEnd = i
                break
            }
        }
        val functionHead = s.substring(1, indexEnd)
        return functionHead
    }

    private fun getThisLine(editor: Editor): String {
        val document = editor.document
        val caretModel = editor.caretModel
        val caretOffset = caretModel.offset
        val lineNum = document.getLineNumber(caretOffset)
        val lineStartOffset = document.getLineStartOffset(lineNum)
        val lineEndOffset = document.getLineEndOffset(lineNum)
        return document.getText(TextRange(lineStartOffset, lineEndOffset))
    }

    private fun stringFactory(source: String, functionStructure: String): String {
        val beginBeforeEachLine = source.beginSpaces()
        val sb = StringBuffer()
        sb.append(source)
        sb.append(NEXT_LINE)
        functionStructure.splitForParams().forEach {
            println("AAAAAAAA->$it")
            sb.append(beginBeforeEachLine)
            sb.append(LINE_INNER)
            sb.append(PARAM)
            sb.append(it)
            sb.append(NEXT_LINE)
        }
        sb.append(beginBeforeEachLine)
        sb.append(LINE_END)

        return sb.toString()
    }
}
