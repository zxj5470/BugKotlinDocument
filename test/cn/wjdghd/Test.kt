package cn.wjdghd

import com.intellij.util.containers.Stack

val charStack = Stack<Char>()

fun main(args: Array<String>) {
    var sub = """package cn.wjdghd
import javax.servlet.http.*
    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.doPost(req, resp)
    }
}"""
    val index = sub.indexOf('(')
    val s = sub.substring(index)
    var top: Char
    //only to get end index of reality ')' )
    var indexEnd: Int = 0
    for(i in s.indices){
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
//    println(functionHead)
    charStack.clear()

    val eachForList = functionHead.splitForParams()
//    eachForList.map { println(it) }
}

