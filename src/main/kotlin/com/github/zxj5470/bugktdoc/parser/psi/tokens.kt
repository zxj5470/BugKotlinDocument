package com.github.zxj5470.bugktdoc.parser.psi

import java.io.File

/**
 * @author: zxj5470
 * @date: 2018/4/2
 */
object BugKtDocToken {
	object Annotation {
		const val THROWS = "@Throws"
	}

	object Symbol {
		const val COMMMA = ","
		const val DOUBLE_COLON = "::"
		const val SINGLE_COLON = ":"

	}

	object KeyWord {
		const val CLASS = "class"
		const val FUN = "fun"
	}

	object Modifier {
		val set = File("kotlinModifier.txt").readLines()
	}

}