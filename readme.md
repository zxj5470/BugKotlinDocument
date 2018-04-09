# Plugin for IntelliJ IDEA in Kotlin File

- Generate document comments for Kotlin file.
- Just generate it as Java do.

[![JetBrains plugins](https://img.shields.io/jetbrains/plugin/v/9781-bugkotlindocument.svg?style=flat-square)](https://plugins.jetbrains.com/plugin/9781-bugkotlindocument)
[![JetBrains plugins](https://img.shields.io/jetbrains/plugin/d/9781-bugkotlindocument.svg?style=flat-square)](https://plugins.jetbrains.com/plugin/9781-bugkotlindocument)

[issues](https://github.com/zxj5470/BugKotlinDocument/issues)

# Usage 

**Just press Enter key like in Java File !!!**

![gif](images/pic01.gif) 

# Screenshots
![png](images/pic02.png) 

# Install
Download the release page then install the plugin.

`Preference -> Plugin ->`

- `Install plugin from disk` in Idea/Android Studio
then enjoy Bugs!!!!

- Or `browse repositories` and search `Kotlin`/`Bug`/`Docu` is OK.

# Samples

- **vararg**

```kotlin
/**
 *
 * @param args Array<out String>
 */
fun main(vararg args: String) {

}
```


- **function**

```kotlin
/**
 *
 * @param i Int
 * @param j Int
 * @return String
 */
fun twoParamsWithReturn(i: Int, j: Int): String {
	return "${i + j}"
}
```

- **class constructors**

```kotlin
/**
 *
 * @param T
 * @property strings Array<out String>
 * @property aInt Int
 * @property bInt Int
 * @constructor
 */
class A<T>(t: String, private vararg val strings: String) {
	/**
	 *
	 * @param name String
	 * @constructor
	 */
	constructor(name: String) : this(name, "") {

	}

	private val aInt = 0
	val bInt = 1
}
```

more samples:

[main.kt](src/test/kotlin/com/github/zxj5470/bugktdoc/samples/main.kt)

[functions.kt](src/test/kotlin/com/github/zxj5470/bugktdoc/samples/functions.kt)

[constructors.kt](src/test/kotlin/com/github/zxj5470/bugktdoc/samples/constructors.kt)