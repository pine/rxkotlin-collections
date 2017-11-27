package moe.pine.rx.collections

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction


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

fun <T> Observable<Iterable<T>>.flatten(): Observable<T> {
    return this.flatMapIterable { it }
}

fun <T> Observable<out T>.forEachIndexed(action: (Int, T) -> Unit) {
    this.withIndex().forEach { action(it.index, it.value) }
}

fun <T> Observable<T>.isNotEmpty(): Single<Boolean> {
    return this.isEmpty.map { !it }
}

fun <T, R> Observable<out T>.mapIndexed(transform: (Int, T) -> R): Observable<R> {
    return this.withIndex().map { transform(it.index, it.value) }
}


fun <T> Observable<T>.none(): Single<Boolean> {
    return this.isEmpty
}

fun <T> Observable<T>.none(predicate: (T) -> Boolean): Single<Boolean> {
    return this.filter(predicate).isEmpty
}

fun <T> Observable<T>?.orEmpty(): Observable<T> {
    return this ?: Observable.empty()
}

fun <T> Observable<T>.reduceIndexed(operation: (Int, T, T) -> T): Maybe<T> {
    return this.withIndex().reduce { accumulator, value ->
        IndexedValue(value.index, operation(value.index, accumulator.value, value.value))
    }.map { it.value }
}

fun <S, T : S> Observable<T>.reduceIndexed(initialValue: S, operation: (Int, S, T) -> T): Single<S> {
    return this.withIndex().reduce(initialValue) { accumulator, value ->
        operation(value.index, accumulator, value.value)
    }
}

fun <T> Observable<out T>.withIndex(): Observable<IndexedValue<T>> {
    return this.zipWith(
            Observable.range(0, Int.MAX_VALUE),
            BiFunction { value, index -> IndexedValue(index, value) }
    )
}
