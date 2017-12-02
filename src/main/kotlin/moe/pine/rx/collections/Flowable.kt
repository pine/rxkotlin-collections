package moe.pine.rx.collections

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Collection Utils for Flowable
 * Created by pine on 2017/11/29.
 */


fun <T> Flowable<T>.filterIndexed(predicate: (Int, T) -> Boolean): Flowable<T> {
    return this.withIndex().filter { predicate(it.index, it.value) }.map { it.value }
}

inline fun <reified R> Flowable<*>.filterIsInstance(): Flowable<R> {
    return this.filter { it is R }.map { it as R }
}

fun <R> Flowable<*>.filterIsInstance(klass: Class<R>): Flowable<R> {
    @Suppress("UNCHECKED_CAST")
    return this.filter { klass.isInstance(it) }.map { it as R }
}

fun <T> Flowable<T>.filterNot(predicate: (T) -> Boolean): Flowable<T> {
    return this.filter { !predicate(it) }
}

fun <T> Flowable<Iterable<T>>.flatten(): Flowable<T> {
    return this.flatMapIterable { it }
}

fun <T> Flowable<out T>.forEachIndexed(action: (Int, T) -> Unit) {
    this.withIndex().forEach { action(it.index, it.value) }
}

fun <T> Flowable<T>.isNotEmpty(): Single<Boolean> {
    return this.isEmpty.map { !it }
}

fun <T, R> Flowable<out T>.mapIndexed(transform: (Int, T) -> R): Flowable<R> {
    return this.withIndex().map { transform(it.index, it.value) }
}

fun <T> Flowable<T>.none(): Single<Boolean> {
    return this.isEmpty
}

fun <T> Flowable<T>.none(predicate: (T) -> Boolean): Single<Boolean> {
    return this.filter(predicate).isEmpty
}

fun <T> Flowable<T>?.orEmpty(): Flowable<T> {
    return this ?: Flowable.empty()
}

fun <T> Flowable<T>.reduceIndexed(operation: (Int, T, T) -> T): Maybe<T> {
    return this.withIndex().reduce { accumulator, value ->
        IndexedValue(value.index, operation(value.index, accumulator.value, value.value))
    }.map { it.value }
}

fun <S, T : S> Flowable<T>.reduceIndexed(initialValue: S, operation: (Int, S, T) -> T): Single<S> {
    return this.withIndex().reduce(initialValue) { accumulator, value ->
        operation(value.index, accumulator, value.value)
    }
}

fun <T> Flowable<out T>.withIndex(): Flowable<IndexedValue<T>> {
    return this.zipWith(
            Flowable.range(0, Int.MAX_VALUE),
            BiFunction { value, index -> IndexedValue(index, value) }
    )
}
