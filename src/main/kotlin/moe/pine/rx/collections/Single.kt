package moe.pine.rx.collections

import io.reactivex.Observable
import io.reactivex.Single


/**
 * Collection Utils for Single
 * Created by pine on 2016/03/21.
 */

fun <T> Single<Iterable<T>>.flatten(): Observable<T> {
    return this.toObservable().flatten()
}
