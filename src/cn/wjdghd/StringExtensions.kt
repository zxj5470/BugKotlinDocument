package cn.wjdghd

import com.intellij.util.containers.Stack
import java.util.*

//constants
val NEXT_LINE = "\n"
val PARAM = "@param "
val RETURN = "@return"
val DOC_INNER = " * "
val LINE_SPLIT_COLON = " : "
val DOC_END = " */"

fun String.countSpaceNum(tabSpaceNum: Int = 4): Int {
    var count = 0
    for (i in this.indices) {
        if (this[i] == ' ') count++
        else if (this[i] == '\t') count += tabSpaceNum
        else break
    }
    return count
}

fun String.beginSpaces(): String {
    val numberOfSpace = countSpaceNum()
    val sb = StringBuilder()
    for (i in 0..numberOfSpace) {
        sb.append(' ')
    }
    return sb.toString()
}

fun String.splitForParams(): LinkedList<String> {
    val charStack = Stack<Char>()
    val split = LinkedList<String>()
    var index: Int = 0
    var top: Char

    loop@ for (it in this.indices) {
        top = if (charStack.empty()) '-' else charStack.peek()
        when (this[it]) {
            '\'' -> {
                if (top != '\'') charStack.push(this[it])
                else charStack.pop()
            }
            '\"' -> {
                if (top != '\"') charStack.push(this[it])
                else charStack.pop()
            }
            '(', '{', '<' -> charStack.push(this[it])
            ')' -> {
                if (top == '(') {
                    charStack.pop()
                    if (charStack.empty()) break@loop
                }
            }
            '}' -> if (top == '{') charStack.pop()
            '>' -> if (top == '<') charStack.pop()
            ',' -> {
                if (top != '<' && top != '{') {
                    split.addLast(this.substring(index, it).replace(" ", "").replace(":", " "))
                    index = it + 1
                }
            }
        }
    }
    split.addLast(this.substring(index).replace(" ", "").replace("::", " ").replace(":", " "))
    return split
}

fun String.ifBeginWith(beginString: String) = this.indexOf(beginString) == 0