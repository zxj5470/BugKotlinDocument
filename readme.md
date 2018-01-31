# Plugin for IntelliJ IDEA in Kotlin File

- Generate notation in Kotlin file.
- It still remains lots of bugs and most of works are not completed and finished...Keep fighting.
- I will finish this after `julia-intellij` plugin

[![JetBrains plugins](https://img.shields.io/jetbrains/plugin/v/9781-bugkotlindocument.svg)](https://plugins.jetbrains.com/plugin/10413-julia)
[![JetBrains plugins](https://img.shields.io/jetbrains/plugin/d/9781-bugkotlindocument.svg)](https://plugins.jetbrains.com/plugin/9781-bugkotlindocument)

[issues](https://github.com/zxj5470/BugKotlinDocument/issues)



# Examples:
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
/** can't generate it.
fun String.ifBeginWith(beginString:String)=this.indexOf(beginString)==0

*//** Should it be OK ?
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
fun String.splitWithParams(): LinkedList<String> {/*code*/}

```
# Single Download
```
git clone https://www.github.com/zxj5470/BugKotlinDocument
```
`Preference -> Plugin -> Install plugin from disk` in Idea/Android Studio
and find `plugin.jar`  
then enjoy Bugs!!!!

# For Developer
<del>
You should 
copy the `workspace.xml` to 
`.idea/workspace.xml`
otherwise the project won't run as a plugin!!!!
</del>
You can just build by gradle,so ignore that.

# THe End

That's too naïve.It's a Canary.

Maybe I'll go further.Who cares?

Heh~

Interesting~

