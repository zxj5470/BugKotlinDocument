package com.github.zxj5470.bugktdoc.generator

import com.github.zxj5470.bugktdoc.castTo
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocModel as Model
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
				append(indents)
				append(Doc.INNER)
				append(Decorator.THROWS)
				append(Doc.SPACE)
				append(it)
				append(Doc.LF)
			}
		}
	}

	/**
	 * @param paramAndType List<String> : a list like [ "a Int : ","b String : " ]
	 */
	private fun generateFunctionParamDoc(paramAndType: List<String>, indents: String = "", extensionFunctionType: String = ""): String = buildString {
		if (extensionFunctionType.isNotEmpty()) {
			append(indents)
			append(Doc.INNER)
			append(Decorator.RECEIVER)
			append(Doc.SPACE)
			append(extensionFunctionType)
			append(Doc.LF)
		}
		if (paramAndType.isNotEmpty()) {
			paramAndType.forEach {
				append(indents)
				append(Doc.INNER)
				append(Decorator.PARAM)
				append(Doc.SPACE)
				append(it)
				append(Doc.LF)
			}
		}
	}

	/**
	 * @param properties List<String> : a list like [ "a Int : ","b String :" ], seems the same to function doc.
	 */
	private fun generateClassDoc(properties: List<String>, genericType: String, indents: String = ""): String = buildString {
		if (genericType.isNotEmpty()) {
			append(Doc.INNER)
			append(Decorator.PARAM)
			append(Doc.SPACE)
			append(genericType)
			append(Doc.LF)
		}

		if (properties.isNotEmpty()) {
			properties.forEach {
				append(indents)
				append(Doc.INNER)
				append(Decorator.PROPERTY)
				append(Doc.SPACE)
				append(it)
				append(Doc.LF)
			}
		}
	}

	//No Use by auto-complete
	private fun generateEnd() = buildString {
	}

	private fun generateReturn(returnType: String, indents: String = "") = buildString {
	}

	fun generate(type: DocType, entity: Any, indents: String = ""): String = buildString {
		when (type) {
			DocType.FUNCTION -> {
				entity.castTo<Model.FunctionModel> {
					append(generateThrowsDoc(throwsList, indents))
					append(generateFunctionParamDoc(paramAndTyleList, indents, extensionFunctionType))
				}
			}
			DocType.CLASS -> {
				entity.castTo<Model.ClassModel> {
					append(generateClassDoc(properties, genericType, indents))
				}
			}
			DocType.VARIABLE -> {

			}
		}
	}
}