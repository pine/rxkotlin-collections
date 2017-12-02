# RxKotlin Collections
[![Build Status](https://img.shields.io/travis/pine/rxkotlin-collections/master.svg)](https://travis-ci.org/pine/rxkotlin-collections.svg?branch=master) [![Coverage Status](https://img.shields.io/coveralls/pine/rxkotlin-collections/master.svg)](https://coveralls.io/github/pine/rxkotlin-collections?branch=master) [![Dependency Status](https://img.shields.io/versioneye/d/user/projects/56f2a16f35630e0034fd9c8a.svg)](https://www.versioneye.com/user/projects/56f2a16f35630e0034fd9c8a)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fpine%2Frxkotlin-collections.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fpine%2Frxkotlin-collections?ref=badge_shield)

Kotlin Collections Methods for [RxJava2](https://github.com/ReactiveX/RxJava).

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
import moe.pine.rx.collections.filterIndexed
import io.reactivex.Observable

val observable: Observable<String> = Observable.fromArray("a", "b", "c")
observable.filterIndexed { i, value -> i % 2 == 0 }.subscribe { println(it) }
// => "a", "c"
```

In addition, this library provides an extensions of the following.

### Observable
- filterIndexed
- filterIsInstance
- filterNot
- flatten
- forEachIndexed
- isNotEmpty
- mapIndexed
- none
- orEmpty
- reduceIndexed
- withIndex

### Flowable
- filterIndexed
- filterIsInstance
- filterNot
- flatten
- forEachIndexed
- isNotEmpty
- mapIndexed
- none
- orEmpty
- reduceIndexed
- withIndex

## Test

```
$ ./gradlew clean test
```

## Upload Bintray

```
$ ./gradlew clean assemble bintrayUpload
```

## License
MIT &copy; Pine Mizune



[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fpine%2Frxkotlin-collections.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fpine%2Frxkotlin-collections?ref=badge_large)
