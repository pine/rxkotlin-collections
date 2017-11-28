package moe.pine.rx.collections

import io.reactivex.Flowable

/**
 * Collection Utils for Flowable
 * Created by pine on 2017/11/29.
 */


fun <T> Flowable<T>?.orEmpty(): Flowable<T> {
    return this ?: Flowable.empty()
}