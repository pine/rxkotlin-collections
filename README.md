# RxKotlin Collections
[![Bintray](https://img.shields.io/bintray/v/pinemz/maven/rxkotlin-collections.svg?style=flat-square)](https://bintray.com/pinemz/maven/rxkotlin-collections/view) [![Build Status](https://img.shields.io/travis/pine/rxkotlin-collections/master.svg?style=flat-square)](https://travis-ci.org/pine/rxkotlin-collections) [![Coverage Status](https://img.shields.io/coveralls/pine/rxkotlin-collections/master.svg?style=flat-square)](https://coveralls.io/github/pine/rxkotlin-collections?branch=master) [![Dependency Status](https://img.shields.io/versioneye/d/user/projects/56f2a16f35630e0034fd9c8a.svg?style=flat-square)](https://www.versioneye.com/user/projects/56f2a16f35630e0034fd9c8a)

Kotlin Collections Methods for [RxJava](https://github.com/ReactiveX/RxJava).

## Getting Started
Please type it in your build.gradle file.

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'moe.pine:rxkotlin-collections:0.2.9'
}
```

## Usage
You can use Observable / Single extensions like [Kotlin collections](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/) as the following.

```kotlin
import moe.pine.rx.collections.filterNotNull
import rx.Observable

val observable: Observable<String?> = Observable.from(listOf("a", null, "c"))
observable.filterNotNull().subscribe { println(it) }
// => "a", "c"
```

In addition, this library provides an extensions of the following.

### Observable
- filterIndexed
- filterIsInstance
- filterNot
- filterNotNull
- firstOrNull
- flatten
- forEachIndexed
- isNotEmpty
- mapIndexed
- mapIndexedNotNull
- mapNotNull
- none
- orEmpty
- reduceIndexed
- requireNotNulls
- withIndex

### Single
- flatten
- requireNoNulls

## Test

```
$ ./gradlew clean test
```

## Upload Bintray

```
$ ./gradlew clean assemble bintrayUpload
```

## License
MIT License
