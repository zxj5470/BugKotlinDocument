package com.github.zxj5470.bugktdoc.parser

import com.github.zxj5470.bugktdoc.constants.BugKtDocControl.SPACE
import com.github.zxj5470.bugktdoc.parser.psi.*
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocToken.ANNOTATION_THROWS
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocToken.KeyWord
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocToken.Symbol
import com.github.zxj5470.bugktdoc.util.clear
import com.github.zxj5470.bugktdoc.util.getTextAfter
import com.github.zxj5470.bugktdoc.util.log
import com.intellij.lang.PsiBuilderFactory
import com.intellij.openapi.editor.Editor
import org.jetbrains.kotlin.lexer.KotlinLexer
import org.jetbrains.kotlin.parsing.KotlinParserDefinition
import java.util.*
import com.github.zxj5470.bugktdoc.parser.psi.BugKtDocStatus as Status

object BugKtDocParser {
	fun parse(editor: Editor): BugKtDocModel {
		var entity: BugKtDocModel = TempEmpty()
		var hugify = 0
		val throwsList = LinkedList<String>()
		val functionModel = FunctionModel()
		val classModel = ClassModel()
		var currentStatus: Status = Status.NOTHING

		val paramsList = LinkedList<String>()
		val buffer = StringBuilder()

		try {
			val code = getTextAfter(editor)
			val psiBuilder = PsiBuilderFactory.getInstance().createBuilder(
				KotlinParserDefinition(), KotlinLexer(), code).apply { mark() }
			loop@ do {
				val type = psiBuilder.tokenType ?: continue
				val text = psiBuilder.tokenText ?: continue
				println("$type $text")
				println("current $currentStatus")
				when (currentStatus) {
					Status.NOTHING -> {
						currentStatus = when (text) {
							Symbol.AT -> Status.DECORATE_AT
							KeyWord.FUN -> Status.FUN
							KeyWord.CLASS -> Status.CLASS
							KeyWord.INTERFACE -> Status.OK
							else -> continue@loop
						}
					}

					Status.DECORATE_AT ->
						currentStatus =
							when (text) {
								ANNOTATION_THROWS -> Status.DECORATING
								else -> Status.NOTHING
							}

					Status.DECORATING -> {
						when {
							text == Symbol.RPAR -> currentStatus = Status.FUN
							type.toString() == "IDENTIFIER" -> throwsList.add(text)
						}
					}

				// TODO: handle function

					Status.FUN -> {
						// hugify. Optional generics
						if (text == Symbol.LT) {
							currentStatus = Status.FUN_GENERICS
							hugify++
						} else {
							currentStatus = Status.FUN_NAME
							buffer.append(text)
						}
					}

					Status.FUN_GENERICS -> {
						when (text) {
							Symbol.LT -> hugify++
							Symbol.GT -> if (--hugify == 0) {
								currentStatus = Status.FUN_NAME
							}
							else -> {
								// KDoc seems no descriptions about generics in function.
								// so we don't handle the generics.
								// only to make sure generics in function is correct.
								currentStatus = Status.FUN_NAME
							}
						}
					}

				// We don't handle function name but a receiver for extension function
					Status.FUN_NAME -> {
						if (text != Symbol.LPAR) {
							buffer.append(text)
						} else {
							// before enter params
							if (buffer.contains(Symbol.DOT)) {
								functionModel.receiver = buffer.toString().substringBeforeLast(Symbol.DOT)
							}
							buffer.clear()
							currentStatus = Status.FUN_PARAM_NAME
						}
					}

					Status.FUN_PARAM_NAME -> {
						// no parameters for a function
						if (paramsList.isEmpty() && text == Symbol.RPAR) {
							currentStatus = Status.FUN_RETURN_TYPE
						} else {
							// not empty. append identifier
							buffer.append(text)
							currentStatus = Status.FUN_PARAM_NAME_TYPE_COLON
						}
					}

					Status.FUN_PARAM_NAME_TYPE_COLON -> {
						if (text == Symbol.COLON) {
							currentStatus = Status.FUN_PARAM_NAME_TYPE
						}
					}

					Status.FUN_PARAM_NAME_TYPE -> {
						when (text) {
						// , = ) :
							Symbol.COLON, Symbol.COMMA, Symbol.RPAR, Symbol.ASSIGN -> {
								if (hugify == 0) {
									paramsList.add(buffer.toString())
									currentStatus = when (text) {
									// , means next param
										Symbol.COMMA -> Status.FUN_PARAM_NAME
									// = means to find current param's default value
										Symbol.ASSIGN -> Status.FUN_PARAM_ASSIGN
									// ) means to find return type
										Symbol.RPAR -> {
											Status.FUN_RETURN_TYPE
										}
										else -> currentStatus
									}
								}
								//TODO: still in < Type >
								else {
									buffer.append(text)
								}
							}
						// append param type identifier, hugify??
							Symbol.LT -> {
								hugify++
							}
							Symbol.GT -> {
								hugify--
							}
							else -> {
								buffer.append(SPACE)
								buffer.append(text.apply { log("TEXT") })
							}
						}
					}

					Status.FUN_PARAM_ASSIGN -> {

					}

					Status.FUN_RETURN_TYPE -> {
						// = is for compact function and { is for simple function and : is for type
						if (text != Symbol.ASSIGN && text != Symbol.LBRACE && text != Symbol.COLON) {
							buffer.append(text)
						} else if (text == Symbol.COLON) {
							continue@loop
						} else {
							currentStatus = Status.FUN_OK
						}
					}

					Status.FUN_OK -> {
						functionModel.throwsList = throwsList
						functionModel.returnType = buffer.toString()
						functionModel.paramAndTypeList = paramsList
						buffer.clear()
						entity = functionModel
						currentStatus = Status.OK
					}

				// TODO: handle class

					Status.CLASS -> {

					}

					Status.CLASS_OK -> {
						entity = classModel
					}

				// TODO: handle variable

					Status.OK -> break@loop

					else -> {

					}
				}

			} while (psiBuilder.apply { advanceLexer() }.tokenType != null)

			// no errors for parser
			if (currentStatus == Status.OK) {
				println(functionModel)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return entity
	}
}