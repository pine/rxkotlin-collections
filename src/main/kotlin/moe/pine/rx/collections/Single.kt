package moe.pine.rx.collections

import rx.Observable
import rx.Single

/**
 * Collection Utils for Single
 * Created by pine on 2016/03/21.
 */
fun <T> Single<Iterable<T>>.flatten(): Observable<T> {
    return this.flatMapObservable { Observable.from(it) }
}

fun <T : Any> Single<T?>.requireNoNulls(): Single<T> {
    return this.map {
        if (it == null) throw IllegalArgumentException("null element found in $this.")
        it
    }
}