package cn.wjdghd.debug

fun String.log(str: String = "") {
    println("------$str--------")
    println(this)
    println("--------------")
}