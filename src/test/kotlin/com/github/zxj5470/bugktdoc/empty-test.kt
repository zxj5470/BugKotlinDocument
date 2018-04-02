package com.github.zxj5470.bugktdoc

import org.junit.Test
import java.io.File

/**
 * @author: zxj5470
 * @date: 2018/4/2
 */
class TestNothing {
	@Test
	fun test() {
		File("kotlinModifiers.txt").readLines().distinct().forEach(::println)
	}
}