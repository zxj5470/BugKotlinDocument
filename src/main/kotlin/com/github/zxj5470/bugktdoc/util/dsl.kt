package com.github.zxj5470.bugktdoc.util

operator fun Boolean.invoke(block: () -> Unit): Boolean {
	if (this) block()
	return this
}