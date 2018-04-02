package com.github.zxj5470.bugktdoc.parser.psi

/**
 * @author zxj5470
 * @data 2018/04/03
 */
class TempEmpty : BugKtDocModel

/**
 * @property paramAndTypeList List<String> :
 */
data class FunctionModel(var paramAndTypeList: List<String> = emptyList(),
								 var throwsList: List<String> = emptyList(),
								 var returnType: String = "",
								 var receiver: String = "") : BugKtDocModel

data class ClassModel(var properties: List<String> = emptyList(),
							 var genericType: String = "") : BugKtDocModel

class VariableModel() : BugKtDocModel
