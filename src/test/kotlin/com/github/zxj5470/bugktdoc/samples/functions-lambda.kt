package com.github.zxj5470.bugktdoc.samples

/**
 * @author zxj5470
 * @date 27/05/2018
 */

/**
 *
 * @param block Function0<Unit>
 */
fun lambda(block: () -> Unit) {

}

/**
 *
 * @param block [@kotlin.ExtensionFunctionType] Function1<String, Unit>
 */
fun lambda(block: String.() -> Unit) {

}

/**
 *
 * @param block [@kotlin.ExtensionFunctionType] Function1<Pair<String, Int>, Unit>
 */
fun lambda2(block: Pair<String, Int>.() -> Unit = {}) {

}

/**
 *
 * @param block Function2<String, Int, Unit>
 */
fun lambda2(block: (String, Int) -> Unit) {

}