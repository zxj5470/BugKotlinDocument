package com.github.zxj5470.bugktdoc.options

/**
 * @author: zxj5470
 * @date: 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm() {
	override fun isModified(): Boolean {
		return false
	}

	override fun getDisplayName() = "BugKotlinDocument"

	override fun apply() {

	}

	override fun createComponent() = mainPanel

}