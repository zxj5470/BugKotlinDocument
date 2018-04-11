@file:Suppress("SENSELESS_COMPARISON")

package com.github.zxj5470.bugktdoc.options

import com.github.zxj5470.bugktdoc.BugKtDocBundle
import com.github.zxj5470.bugktdoc.globalSettings
import com.intellij.ui.layout.verticalPanel
import javax.swing.JCheckBox

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm() {
	init {
		// what the hell it is in CLion?
		// mainPanel is null in CLion.
		if (mainPanel == null) {
			mainPanel = verticalPanel { }
			useBugKtDoc = JCheckBox(BugKtDocBundle.message("bugktdoc.options.use"))
				.apply { mainPanel.add(this) }
			showUnitTypeDefault = JCheckBox(BugKtDocBundle.message("bugktdoc.options.default.unit"))
				.apply { mainPanel.add(this) }
			showClassFieldProperty = JCheckBox(BugKtDocBundle.message("bugktdoc.options.default.property"))
				.apply { mainPanel.add(this) }
			showConstructor = JCheckBox(BugKtDocBundle.message("bugktdoc.options.default.constructor"))
				.apply { mainPanel.add(this) }
		}
		useBugKtDoc.addActionListener {
			showUnitTypeDefault.isEnabled = !showUnitTypeDefault.isEnabled
			showClassFieldProperty.isEnabled = !showClassFieldProperty.isEnabled
			showConstructor.isEnabled = !showConstructor.isEnabled
		}
		loadSettings()
	}

	private fun loadSettings() {
		useBugKtDoc.isSelected = globalSettings.useBugKtDoc
		showUnitTypeDefault.isSelected = globalSettings.alwaysShowUnitReturnType
		showClassFieldProperty.isSelected = globalSettings.alwaysShowClassFieldProperty
		showConstructor.isSelected = globalSettings.alwaysShowConstructor
	}

	override fun isModified(): Boolean {
		return true
	}

	override fun reset() {
		globalSettings.useBugKtDoc = true
		globalSettings.alwaysShowUnitReturnType = false
		globalSettings.alwaysShowClassFieldProperty = true
		globalSettings.alwaysShowConstructor = true
		loadSettings()
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