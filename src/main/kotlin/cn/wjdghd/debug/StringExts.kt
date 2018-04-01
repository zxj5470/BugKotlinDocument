package cn.wjdghd.debug

fun String.log(tag: String = "") {
	println("------$tag--------")
    println(this)
    println("--------------")
}