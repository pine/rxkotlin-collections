# RxKotlin Collections [![Bintray](https://img.shields.io/bintray/v/pinemz/maven/rxkotlin-collections.svg)](https://bintray.com/pinemz/maven/rxkotlin-collections/view) [![Build Status](https://travis-ci.org/pine613/rxkotlin-collections.svg?branch=master)](https://travis-ci.org/pine613/rxkotlin-collections) [![Coverage Status](https://coveralls.io/repos/github/pine613/rxkotlin-collections/badge.svg?branch=master)](https://coveralls.io/github/pine613/rxkotlin-collections?branch=master)
Kotlin Collections Methods for RxJava.

## Getting Started
Please type it in your build.gradle file.

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'moe.pine:rxkotlin-collections:0.0.3'
}
```

## Usage
You can use Observable extensions like [Kotlin collections](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/) as the following.

```kotlin
import moe.pine.rx.collections.filterNotNull
import rx.Observable

val observable: Observable<String?> = Observable.from(listOf("a", null, "c"))
observable.filterNotNull().subscribe { println(it) }
// => "a", "c"
```

In addition, this library provides an extensions of the following.

- filterIndexed
- filterIsInstance
- filterNot
- filterNotNull
- flatten
- forEachIndexed
- isNotEmpty
- mapIndexed
- mapIndexedNotNull
- mapNotNull
- none
- requiresNotNull
- withIndex

## Test
```
$ ./gradlew clean test
```

## License
MIT License
