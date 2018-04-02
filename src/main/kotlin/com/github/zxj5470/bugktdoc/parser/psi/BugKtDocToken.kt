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
		const val ASSIGN = "="
		const val AT_SYMBOL = "@"
		const val COMMA = ","
		const val DOUBLE_COLON = "::"
		const val EQUAL = "=="
		const val SINGLE_COLON = ":"
		// generics
		const val LESS_THAN = "<"
		const val LARGER_THAN = ">"
		// braces
		const val BRACE_L = "("
		const val BRACE_R = ")"
		const val BRACKET_L = "{"
		const val BRACKET_R = "}"
		const val SQUARE_BRACKET_L = "["
		const val SQUARE_BRACKET_R = "]"
	}

	object KeyWord {
		val sets by lazy {
			listOf("class", "fun")
		}
	}

	object Modifiers {
		val sets by lazy {
			File("kotlinModifier.txt").readLines()
		}
	}

}