package cn.wjdghd

import cn.wjdghd.constants.RuntimeConstants.*
import cn.wjdghd.entity.beginSpaces
import cn.wjdghd.entity.splitWithParams
import cn.wjdghd.utils.*

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.MessageDialogBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.util.containers.Stack

class MainComponent : ApplicationComponent {
    lateinit var editor: Editor
    lateinit var document: Document

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
        editor = event.getData(PlatformDataKeys.EDITOR) ?: return

        println(23333)
        lg.debug("23333")
        // thisLine : get /** with spaces
        val thisLine = getThisLine(editor)

        val realNextLine = getRealNextLine(editor)
        val realNext = getRealNext(editor)

        //avoid to mul-replaced
        if (realNextLine.trim()[0] == '*') return

        if (thisLine.trim() == "/**") {
            document = editor.document
            lg.debug("debug:")
            println("thisLine:----------\n$thisLine")
            println("realNext:----------\n$realNext")
            println("realNextLine:----------\n$realNextLine")

            //replace the first line /** to ` /** with @param `
            //via matching @realNext which is the whole function block.

            val stringFac = stringFactory(thisLine, realNextLine, realNext)
            val replaceString = document.text.replace(realNext, stringFac+ thisLine)

            //can be undone
            ApplicationManager.getApplication().runWriteAction {
                CommandProcessor.getInstance().runUndoTransparentAction {
                    this.document.setText(replaceString)
                }
            }
        }
    }


    fun stringFactory(thisLine: String, realNextLine: String, realNext: String): String {
        val beginBeforeEachLine = realNextLine.beginSpaces()
        val sb = StringBuilder()
        sb.append(beginBeforeEachLine)
        sb.append(thisLine.trim())
        sb.append(NEXT_LINE)

        val r = getFunctionDeclarationLine(realNextLine)
        val stringLines=r.splitWithParams()
        stringLines.forEach {
            sb.append(beginBeforeEachLine)
            sb.append(DOC_INNER)
            sb.append(PARAM)
            sb.append(it)
            sb.append(LINE_SPLIT_COLON)
            sb.append(NEXT_LINE)
        }
        sb.append(beginBeforeEachLine)
        sb.append(DOC_END)
        return realNext.replace(thisLine, sb.toString())
    }

    private fun getFunctionDeclarationLine(resource: String): String {
        val sub = resource
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

    private fun getRealNext(editor: Editor): String {
        val document = editor.document
        val caretModel = editor.caretModel
        val caretOffset = caretModel.offset
        val lineNum = document.getLineNumber(caretOffset)
        val lineStartOffset = document.getLineStartOffset(lineNum)
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
                '(', '{', '<' -> charStack.push(s[i])
                ')' -> if (top == '(') charStack.pop()
                '}' -> if (top == '{') charStack.pop()
                '>' -> if (top == '<') charStack.pop()
            }
            if (charStack.isEmpty()) {
                if (s[i] == '}') {
                    indexEnd = i + 1
                    break
                }
            }
        }
        val functionHead = s.substring(0, indexEnd)
        val before = sub.substring(0, index)
        return before + functionHead
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

    private fun getRealNextLine(editor: Editor): String {
        val document = editor.document
        val caretModel = editor.caretModel
        val caretOffset = caretModel.offset
        val lineNum = document.getLineNumber(caretOffset)
        val lineStartOffset = document.getLineStartOffset(lineNum + 1)
        val s = document.text.substring(lineStartOffset)
        val charStack = Stack<Char>()
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
                '(', '{', '<' -> charStack.push(s[i])
                ')' -> if (top == '(') charStack.pop()
                '}' -> if (top == '{') charStack.pop()
                '>' -> if (top == '<') charStack.pop()
            }
            if (charStack.isEmpty()) {
                if (s[i] == '}') {
                    indexEnd = i + 1
                    break
                }
            }
        }
        val functionHead = s.substring(1, indexEnd)
        return functionHead
    }

}
