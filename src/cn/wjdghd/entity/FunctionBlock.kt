package cn.wjdghd.entity

import java.util.*

/**
 * it will be used in v0.2 and replace current matching codes
 */
class FunctionBlock(var wholeBlock: String) {
    var functionArgs: LinkedList<String>? = null
    var throwsArgs: LinkedList<String>? = null
    var isUnitType = true
    var hasThrows = false
}
