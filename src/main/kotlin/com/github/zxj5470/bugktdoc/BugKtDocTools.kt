package com.github.zxj5470.bugktdoc

import com.github.zxj5470.bugktdoc.options.BugKtDocGlobalSettings
import com.intellij.CommonBundle
import com.intellij.openapi.components.ServiceManager
import com.intellij.util.PlatformUtils
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import java.util.*

val globalSettings
	get() = ServiceManager.getService(BugKtDocGlobalSettings::class.java).settings

val isTheFirstTime
	get() = globalSettings.theFirstTile

val pluginActive
	get() = globalSettings.useBugKtDoc

val isAlwaysShowUnitReturnType
	get() = globalSettings.alwaysShowUnitReturnType

val isAlwaysShowClassFieldProperty
	get() = globalSettings.alwaysShowClassFieldProperty

val isAlwaysShowConstructor
	get() = globalSettings.alwaysShowConstructor

/**
 * @ref https://github.com/ice1000/julia-intellij/blob/master/src/org/ice1000/julia/lang/julia-infos.kt
 * class [JuliaBundle]
 */
object BugKtDocBundle {
	@NonNls
	private const val BUNDLE = "BugKtDocBundle"
	private val bundle: ResourceBundle by lazy { ResourceBundle.getBundle(BUNDLE) }
	@JvmStatic
	fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
		CommonBundle.message(bundle, key, *params)
}

inline val KtCallableDeclaration.itsType
	get() = SpecifyTypeExplicitlyIntention.getTypeForDeclaration(this).unwrap().toString()

val isKotlinNative
	get() = PlatformUtils.isAppCode() || PlatformUtils.isCLion()