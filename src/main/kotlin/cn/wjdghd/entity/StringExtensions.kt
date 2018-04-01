package cn.wjdghd.entity

import com.intellij.util.containers.Stack
import java.util.*

fun String.beginSpaces(): String {
	return buildString {
		this@beginSpaces.filter { it in " \t" }.forEach {
			append(it)
		}
	}
}

fun String.splitWithParams(): LinkedList<String> {
    val charStack = Stack<Char>()
    val split = LinkedList<String>()
    var index = 0
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
					val tempStr=this.substring(index, it)
					if(tempStr.contains(" ")){
						tempStr.split(" ").last().replace(":", " ")
					}
                    split.addLast(this.substring(index, it)
							.replace("private ","")
							.replace("public ","")
							.replace("val ","")
							.replace("var ","")
							.replace(" ", "")
							.replace(":", " "))
                    index = it + 1
                }
            }
        }
    }
    split.addLast(this.substring(index)
			.replace("private ","")
			.replace("public ","")
			.replace("val ","")
			.replace("var ","")
			.replace(" ", "")
			.replace("::", " ")
			.replace(":", " "))
    return split
}