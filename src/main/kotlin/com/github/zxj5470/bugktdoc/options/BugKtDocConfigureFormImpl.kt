package com.github.zxj5470.bugktdoc.options

import com.github.zxj5470.bugktdoc.globalSettings

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm() {
	init {
		useBugKtDoc.isSelected = globalSettings.useBugKtDoc
	}

	override fun isModified(): Boolean {
		return true
	}

	override fun getDisplayName() = "BugKotlinDocument"

	override fun apply() {
		globalSettings.useBugKtDoc = useBugKtDoc.isSelected
	}

	override fun createComponent() = mainPanel

}