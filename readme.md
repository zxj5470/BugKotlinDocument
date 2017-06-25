# Plugin for IntelliJ IDEA in Kotlin File
use in Kotlin file to generate document.

it still remains a lot of bug and most of them are not completed

##### v0.1.1 A simple version:
- remove the Gradle so reduce from 16MB to 13KB 
- solve Repeated matching problem and maybe it still remains :joy:
[Problems](#Problems)
### example:
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

# <a name="Problems"></a>Problems

```kotlin
/**
fun String.ifBeginWith(beginString:String)=this.indexOf(beginString)==0



 */
 /** Should it be OK ?
  * @param tabSpaceNum Int=4 : 
  * 
  * ~~~~I want ->
  * @param tabSpaceNum Int : (default is 4 ) 
  * @return Int :
  */
fun String.countSpaceNum(tabSpaceNum: Int = 4): Int{/*code*/}


/** it would not be generated due to @return `LinkedList<String>`
* and what I need is as follow:
* 
* @return LinkedList<String> :
*/
fun String.splitForParams(): LinkedList<String> {/*code*/}


```


# THe End

That's too naïve.It's a Canary.

Maybe I'll go further.Who cares?

Heh~
