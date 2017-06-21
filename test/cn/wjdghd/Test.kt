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

    val m = My()

    println("old_______\n$whole")
    println("new_______\n")
    println(whole.replace(realNext,m.stringFactory(thisLine,nextLine,realNext)))

}

