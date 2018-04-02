package com.github.zxj5470.bugktdoc.parser.psi

object BugKtDocModel {
	/**
	 * @property paramAndTyleList List<String> :
	 */
	class FunctionModel(val paramAndTyleList: List<String>,
							  val throwsList: List<String>,
							  val extensionFunctionType: String = "")

	class ClassModel(val properties: List<String>,
						  val genericType: String)

	class VariableModel()
}