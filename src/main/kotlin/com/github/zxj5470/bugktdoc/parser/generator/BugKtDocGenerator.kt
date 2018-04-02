package com.github.zxj5470.bugktdoc.parser.generator

import com.github.zxj5470.bugktdoc.parser.psi.*
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocModel
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocToken.GenerateType as DocType
import com.github.zxj5470.bugktdoc.constants.BugKtDocControl as Doc
import com.github.zxj5470.bugktdoc.constants.BugKtDocDecoration as Decorator

object BugKtDocGenerator {
	/**
	 * @param exceptions List<String> : a list like [ "IOException","xxxException" ]
	 */
	private fun generateThrowsDoc(exceptions: List<String>, indents: String = ""): String = buildString {
		if (exceptions.isNotEmpty()) {
			exceptions.forEach {
				append(Doc.LF)
				append(indents)
				append(Doc.INNER)
				append(Decorator.THROWS)
				append(Doc.SPACE)
				append(it)
				append(Doc.SPACE)
			}
		}
	}

	/**
	 * @param paramAndType List<String> : a list like [ "a Int","b String" ]
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	 * generate Doc just like this
	 */
	private fun generateFunctionParamDoc(paramAndType: List<String>, indents: String = "", receiver: String = ""): String = buildString {
		if (receiver.isNotEmpty()) {
			append(Doc.LF)
			append(indents)
			append(Doc.INNER)
			append(Decorator.RECEIVER)
			append(Doc.SPACE)
			append(receiver)
			append(Doc.SPACE)
		}
		if (paramAndType.isNotEmpty()) {
			paramAndType.forEach {
				append(Doc.LF)
				append(indents)
				append(Doc.INNER)
				append(Decorator.PARAM)
				append(Doc.SPACE)
				append(it)
				append(Doc.COLON_AFTER_TYPE)
			}
		}
	}

	/**
	 * @param properties List<String> : a list like [ "a Int : ","b String :" ], seems the same to function doc.
	 */
	private fun generateClassDoc(properties: List<String>, genericType: String, indents: String = ""): String = buildString {
		if (genericType.isNotEmpty()) {
			append(Doc.LF)
			append(Doc.INNER)
			append(Decorator.PARAM)
			append(Doc.SPACE)
			append(genericType)
		}

		if (properties.isNotEmpty()) {
			properties.forEach {
				append(Doc.LF)
				append(indents)
				append(Doc.INNER)
				append(Decorator.PROPERTY)
				append(Doc.SPACE)
				append(it)
			}
		}
	}

	//No Use by auto-complete
	private fun generateEnd() = buildString {
	}

	/**
	 * @return String : the generate result
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	 * generate Doc just like this
	 */
	private fun generateReturn(returnType: String, indents: String = "") = buildString {
		if (returnType.isNotEmpty()) {
			append(Doc.LF)
			append(indents)
			append(Doc.INNER)
			append(Decorator.RETURN)
			append(Doc.SPACE)
			append(returnType)
			append(Doc.SPACE)
		}
	}

	fun generate(entity: BugKtDocModel, indents: String = ""): String = buildString {
		when (entity) {
			is FunctionModel -> {
				entity.apply {
					append(generateFunctionParamDoc(paramAndTypeList, indents, receiver))
					append(generateThrowsDoc(throwsList, indents))
					append(generateReturn(returnType, indents))
				}
			}
			is ClassModel -> {
				entity.apply {
					append(generateClassDoc(properties, genericType, indents))
				}
			}
			is VariableModel -> {

			}
		}
	}
}