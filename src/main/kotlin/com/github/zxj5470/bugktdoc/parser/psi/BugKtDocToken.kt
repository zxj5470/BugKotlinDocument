package com.github.zxj5470.bugktdoc.parser.psi

import java.io.File

/**
 * @author zxj5470
 * @date 2018/4/2
 */
object BugKtDocToken {
	enum class GenerateType {
		FUNCTION,
		CLASS,
		VARIABLE
	}

	object Annotations {
		const val THROWS = "@Throws"
	}

	object Symbol {
		const val AT_SYMBOL = "@"
		const val COMMA = ","
		const val DOUBLE_COLON = "::"
		const val SINGLE_COLON = ":"
		val sets = listOf(AT_SYMBOL, COMMA, DOUBLE_COLON, SINGLE_COLON)
	}

	object KeyWord {
		val sets = listOf("class", "fun")
	}

	object Modifiers {
		val sets = File("kotlinModifier.txt").readLines()
	}

}