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

	const val ANNOTATION_THROWS = "Throws"

	object Symbol {
		const val ASSIGN = "="
		const val AT = "@"
		const val COMMA = ","
		const val COLONCOLON = "::"
		const val EQUAL = "=="
		const val COLON = ":"
		const val DOT = "."
		// generics
		const val LT = "<"
		const val GT = ">"
		// braces
		const val LPAR = "("
		const val RPAR = ")"
		const val LBRACE = "{"
		const val RBRACE = "}"
		const val SQUARE_BRACKET_L = "["
		const val SQUARE_BRACKET_R = "]"
	}

	object KeyWord {
		const val FUN = "fun"
		const val CLASS = "class"
		const val INTERFACE = "interface"

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