package com.github.zxj5470.bugktdoc.samples

/**
 *
 * @param T
 * @property strings Array<out String>
 * @property aInt Int
 * @property bInt Int
 * @constructor
 */
class A<T>(t: String, private vararg val strings: String) {
	/**
	 *
	 * @param name String
	 * @constructor
	 */
	constructor(name: String) : this(name, "") {

	}

	private val aInt = 0
	val bInt = 1
}