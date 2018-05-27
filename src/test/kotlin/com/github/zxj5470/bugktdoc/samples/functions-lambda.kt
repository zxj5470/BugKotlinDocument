package com.github.zxj5470.bugktdoc.samples

/**
 * @author zxj5470
 * @date 27/05/2018
 */

/**
 *
 * @param block () -> Unit
 */
fun lambda(block: () -> Unit) {

}

/**
 *
 * @param block String.() -> Unit
 * @return Unit
 */
fun lambda(block: String.() -> Unit) {

}

/**
 *
 * @param block Pair<String, Int>.() -> Unit
 * @return Unit
 */
fun lambda2(block: Pair<String, Int>.() -> Unit = {}) {

}

/**
 *
 * @param block (String, Int) -> Unit
 */
fun lambda2(block: (String, Int) -> Unit) {

}