package com.github.zxj5470.bugktdoc.options

import com.github.zxj5470.bugktdoc.BugKtDocBundle
import com.github.zxj5470.bugktdoc.globalSettings

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm() {
	init {
		useBugKtDoc.isSelected = globalSettings.useBugKtDoc
		showUnitTypeDefault.isSelected = globalSettings.alwaysShowUnitReturnType
	}

	override fun isModified(): Boolean {
		return true
	}

	override fun getDisplayName() = BugKtDocBundle.message("bugktdoc.settings.title")

	override fun apply() {
		globalSettings.useBugKtDoc = useBugKtDoc.isSelected
		globalSettings.alwaysShowUnitReturnType = showUnitTypeDefault.isSelected
	}

	override fun createComponent() = mainPanel

}