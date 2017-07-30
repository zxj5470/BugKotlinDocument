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
    /** Should it be OK ?
    * @param tabSpaceNum Int=4 :
    * @param tabSpaceNum Int : (default is 4 )
    * @return Int :
    */
    fun String.countSpaceNum(tabSpaceNum: Int = 4): Int{
        val a=1
        return a
    }

    fun a():Int{
        return 0
    }
    
    fun b(){
        
    }
}

