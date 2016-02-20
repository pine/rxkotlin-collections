@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("CollectionsKt")

package moe.pine.rx.collections

import rx.Observable

/**
 * Collection Utils for Observable
 * Created by pine on 2016/02/20.
 */
fun <T> Observable<T>.filterIndexed(predicate: (Int, T) -> Boolean): Observable<T> {
    return this.withIndex().filter { predicate(it.index, it.value) }.map { it.value }
}

fun <T> Observable<T>.filterNot(predicate: (T) -> Boolean): Observable<T> {
    return this.filter { !predicate(it) }
}

fun <T : Any> Observable<T?>.filterNotNull(): Observable<T> {
    return this.filter { it != null }.map { it!! }
}

fun <T, R : Any> Observable<T>.mapNotNull(transform: (T) -> R?): Observable<R> {
    return this.map(transform).filterNotNull()
}

fun <T> Observable<T>.none(): Observable<Boolean> {
    return this.isEmpty
}

fun <T> Observable<T>.none(predicate: (T) -> Boolean): Observable<Boolean> {
    return this.filter(predicate).isEmpty
}

fun <T> Observable<T>.withIndex(): Observable<IndexedValue<T>> {
    return Observable.zip(
            Observable.range(0, Int.MAX_VALUE),
            this,
            { index, value -> IndexedValue(index, value) }
    )
}