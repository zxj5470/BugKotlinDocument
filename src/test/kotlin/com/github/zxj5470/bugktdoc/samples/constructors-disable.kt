package com.github.zxj5470.bugktdoc.samples


/**
 * @author zxj5470
 * @date 2018/4/9
 */

/**
 *
 * @param T
 * @property strings Array<out String>
 * @property aInt Int
 * @property bInt Int
 */
class ADisable<T>(t: String, private vararg val strings: String) {

	/**
	 *
	 * @param name String
	 */
	constructor(name: String) : this(name, "") {

	}

	private val aInt = 0
	val bInt = 1
}