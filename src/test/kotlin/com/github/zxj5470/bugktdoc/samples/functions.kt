package com.github.zxj5470.bugktdoc.samples

/**
 *
 * @param i Int
 * @param j Int
 */
fun twoParam(i: Int, j: Int) {

}

/**
 *
 * @param i Int
 * @param j Int
 * @return String
 */
fun twoParamWithReturn(i: Int, j: Int): String {
	return "${i + j}"
}

/**
 *
 * @param i Int
 * @param j Int
 * @return String
 */
fun twoParamWithExpressionReturn(i: Int, j: Int) = "${i + j}"
