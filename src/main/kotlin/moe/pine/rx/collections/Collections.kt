package moe.pine.rx.collections

import rx.Observable

/**
 * Collection Utils for Observable
 * Created by pine on 2016/02/20.
 */
fun <T> Observable<T>.filterIndexed(predicate: (Int, T) -> Boolean): Observable<T> {
    return this.withIndex().filter { predicate(it.index, it.value) }.map { it.value }
}

inline fun <reified R> Observable<*>.filterIsInstance(): Observable<R> {
    return this.filter { it is R }.map { it as R }
}

fun <R> Observable<*>.filterIsInstance(klass: Class<R>): Observable<R> {
    @Suppress("UNCHECKED_CAST")
    return this.filter { klass.isInstance(it) }.map { it as R }
}

fun <T> Observable<T>.filterNot(predicate: (T) -> Boolean): Observable<T> {
    return this.filter { !predicate(it) }
}

fun <T : Any> Observable<out T?>.filterNotNull(): Observable<T> {
    @Suppress("UNCHECKED_CAST")
    return this.filter { it != null }.map { it as T }
}

fun <T> Observable<Iterable<T>>.flatten(): Observable<T> {
    return this.flatMapIterable { it }
}

fun <T> Observable<out T>.forEachIndexed(action : (Int, T) -> Unit) {
    this.withIndex().forEach { action(it.index, it.value) }
}

fun <T> Observable<T>.isNotEmpty(): Observable<Boolean> {
    return this.isEmpty.map { !it }
}

fun <T, R> Observable<out T>.mapIndexed(transform: (Int, T) -> R): Observable<R> {
    return this.withIndex().map { transform(it.index, it.value) }
}

fun <T, R : Any> Observable<out T>.mapIndexedNotNull(transform: (Int, T) -> R?): Observable<R> {
    return this.mapIndexed(transform).filterNotNull()
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

fun <T : Any> Observable<T?>.requireNoNulls(): Observable<T> {
    return this.map {
        if (it == null) throw IllegalArgumentException("null element found in $this.")
        it
    }
}

fun <T> Observable<out T>.withIndex(): Observable<IndexedValue<T>> {
    return Observable.zip(
            Observable.range(0, Int.MAX_VALUE),
            this,
            { index, value -> IndexedValue(index, value) }
    )
}