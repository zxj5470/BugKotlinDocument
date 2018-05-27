package com.github.zxj5470.bugktdoc.util

fun StringBuilder.clear() = this.setLength(0)

fun String.log(tag: String = "") {
	println("------$tag--------")
	println(this)
	println("--------------")
}

operator fun Boolean.invoke(block: () -> Unit): Boolean {
	if (this) block()
	return this
}