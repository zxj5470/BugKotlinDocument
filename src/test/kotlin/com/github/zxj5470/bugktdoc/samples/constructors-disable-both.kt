package com.github.zxj5470.bugktdoc.samples


/**
 * disable both fields `@property` and `@constructor`
 */

/**
 *
 * @param T
 * @property strings Array<out String>
 */
class AFieldDisabled<T>(t: String, private vararg val strings: String) {
	/**
	 *
	 * @param name String
	 */
	constructor(name: String) : this(name, "") {

	}

	private val aInt = 0
	val bInt = 1
}