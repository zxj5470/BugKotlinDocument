# Plugin for IntelliJ IDEA in Kotlin File
use in Kotlin file to generate document.

it still remains a lot of bug and most of them are not completed

example:

#### 1.type the CharSequence "/**"
```kotlin
package cn.wjdghd
import javax.servlet.http.*
     /**             
     override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
         super.doPost(req, resp)
     }
```
#### 2.then press the key (default is Alt+N)
#### 3.and you will see the document.
```kotlin
package cn.wjdghd
import javax.servlet.http.*
    /**
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    */
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        super.doPost(req, resp)
    }
```

That's too naïve.It's a Canary.

Maybe I'll go further.Who cares?

Heh~
