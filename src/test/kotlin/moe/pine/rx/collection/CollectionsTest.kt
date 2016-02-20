package moe.pine.rx.collections

import org.junit.After
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.observers.TestSubscriber

/**
 * Test for Collections Extensions
 * Created by pine on 2016/02/20.
 */
class CollectionsTest {
    @Before
    fun setup() {
    }

    @After
    fun teardown() {
    }

    @Test
    fun filterIndexed() {
        val observable: Observable<String> = Observable.from(listOf("a", "b", "c"))
        TestSubscriber<String>().apply {
            observable.filterIndexed { i, s ->  i == 0 || s == "c" }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues("a", "c")
            this.assertCompleted()
        }
    }

    @Test
    fun filterNot() {
        val observable: Observable<Int> = Observable.from(listOf(1, 2, 3, 4))
        TestSubscriber<Int>().apply {
            observable.filterNot { it % 2 == 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues(1, 3)
            this.assertCompleted()
        }
    }

    @Test
    fun filterNotNull() {
        val observable: Observable<Int?> = Observable.from(listOf(1, null, 3, null))
        TestSubscriber<Int>().apply {
            observable.filterNotNull().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues(1, 3)
            this.assertCompleted()
        }
    }

    @Test
    fun mapNotNull() {
        val observable: Observable<Int> = Observable.from(listOf(1, 2, 3, 4))
        TestSubscriber<Int>().apply {
            observable.mapNotNull { if (it % 2 == 0) null else it }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues(1, 3)
            this.assertCompleted()
        }
    }

    @Test
    fun none() {
        TestSubscriber<Boolean>().apply {
            Observable.empty<Int>().none().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertCompleted()
        }

        TestSubscriber<Boolean>().apply {
            Observable.just(0).none { it != 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertCompleted()
        }

        TestSubscriber<Boolean>().apply {
            Observable.just(0).none().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertCompleted()
        }

        TestSubscriber<Boolean>().apply {
            Observable.just(0).none { it == 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertCompleted()
        }
    }

    @Test
    fun withIndex() {
        val observable = Observable.from(listOf("a", "b", "c"))
        TestSubscriber<IndexedValue<String>>().apply {
            observable.withIndex().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertCompleted()
        }
    }
}