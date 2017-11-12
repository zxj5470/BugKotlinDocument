package cn.wjdghd.entity

import com.intellij.util.containers.Stack
import java.util.*

/**
 * count the Space Number(one tab is for 4 spaces)
 * @param tabSpaceNum Int = 4 :
 */
fun String.countSpaceNum(tabSpaceNum: Int = 4): Int {
    var count = 0
    for (i in this.indices) {
        if (this[i] == ' ') count++
        else if (this[i] == '\t') count += tabSpaceNum
        else break
    }
    return count
}
 /**
  * operator reload `*`
  * @param times Int : the number of times(*)
  */
operator fun String.times(times: Int): String {
    val sb = StringBuilder()
    for (i in 0..times) {
        sb.append(this)
    }
    return sb.toString()
}

fun String.beginSpaces(): String {
    return " " * countSpaceNum()
}

fun String.splitWithParams(): LinkedList<String> {
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