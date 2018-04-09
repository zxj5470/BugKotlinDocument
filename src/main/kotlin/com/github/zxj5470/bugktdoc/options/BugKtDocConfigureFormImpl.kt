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
		useBugKtDoc.addActionListener {
			if (useBugKtDoc.isSelected) {
				showUnitTypeDefault.isEnabled = true
				showClassFieldProperty.isEnabled = true
				showConstructor.isEnabled = true
			} else {
				showUnitTypeDefault.isEnabled = false
				showClassFieldProperty.isEnabled = false
				showConstructor.isEnabled = false
			}
		}
		showUnitTypeDefault.isSelected = globalSettings.alwaysShowUnitReturnType
		showClassFieldProperty.isSelected = globalSettings.alwaysShowClassFieldProperty
		showConstructor.isSelected = globalSettings.alwaysShowConstructor
	}

	override fun isModified(): Boolean {
		return true
	}

	override fun getDisplayName() = BugKtDocBundle.message("bugktdoc.settings.title")

	override fun apply() {
		globalSettings.useBugKtDoc = useBugKtDoc.isSelected
		globalSettings.alwaysShowUnitReturnType = showUnitTypeDefault.isSelected
		globalSettings.alwaysShowClassFieldProperty = showClassFieldProperty.isSelected
		globalSettings.alwaysShowConstructor = showConstructor.isSelected
	}

	override fun createComponent() = mainPanel

}