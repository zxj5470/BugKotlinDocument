package com.github.zxj5470.bugktdoc.options

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * @author: zxj5470
 * @date: 2018/4/2
 */

data class BugKtDocSettings(
	var alwaysShowReturn: Boolean = true,
	var firstTimeToShowPopup: Boolean = false
)

interface BugKtDocGlobalSettings {
	val settings: BugKtDocSettings
}

/**
 * @ref julia-intellij
 */
@State(
	name = "BugKtDocConfig",
	storages = [Storage(file = "BugKtDocConfig.xml", scheme = StorageScheme.DIRECTORY_BASED)])
class BugKtDocGlobalSettingsImpl :
	BugKtDocGlobalSettings, PersistentStateComponent<BugKtDocSettings> {
	override val settings = BugKtDocSettings(false, true)
	override fun getState(): BugKtDocSettings? = XmlSerializerUtil.createCopy(settings)
	override fun loadState(state: BugKtDocSettings) {
		XmlSerializerUtil.copyBean(state, settings)
	}
}