package cn.wjdghd


fun main(args: Array<String>) {
    val whole = """package cn.wjdghd
import javax.servlet.http.*
    /**
    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.doPost(req, resp)
    }
}"""

    val realNext="""    /**
    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.doPost(req, resp)
    }"""
    val thisLine = """    /**"""
    val nextLine = """
    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        super.doPost(req, resp)
    }"""

    val m = MainComponent()

    println("old_______\n$whole")
    println("new_______\n")
}

