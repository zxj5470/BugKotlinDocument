package com.github.zxj5470.bugktdoc.util

fun String.log(tag: String = "") {
	println("------$tag--------")
	println(this)
	println("--------------")
}