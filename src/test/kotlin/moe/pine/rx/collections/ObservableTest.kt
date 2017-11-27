package moe.pine.rx.collections

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Test for Observable Extensions
 * Created by pine on 2016/02/20.
 */
class ObservableTest {
    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun filterIndexed() {
        val observable: Observable<String> = Observable.fromArray("a", "b", "c")
        TestObserver<String>().apply {
            observable.filterIndexed { i, s -> i == 0 || s == "c" }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues("a", "c")
            this.assertComplete()
        }
    }

    @Test
    fun filterIsInstance() {
        val observable: Observable<Any> = Observable.just(1, false, "a")
        TestObserver<Boolean>().apply {
            observable.filterIsInstance<Boolean>().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }

        TestObserver<String>().apply {
            observable.filterIsInstance(String::class.java).subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue("a")
            this.assertComplete()
        }
    }

    @Test
    fun filterNot() {
        val observable: Observable<Int> = Observable.fromArray(1, 2, 3, 4)

        TestObserver<Int>().apply {
            observable.filterNot { it % 2 == 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues(1, 3)
            this.assertComplete()
        }
    }

    @Test
    fun flatten() {
        val observable: Observable<Iterable<String>> = Observable.just(listOf("a", "b", "c"))
        TestObserver<String>().apply {
            observable.flatten().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues("a", "b", "c")
            this.assertComplete()
        }
    }

    @Test
    fun forEachIndexed() {
        val observable: Observable<String> = Observable.fromArray("a", "b", "c")
        TestObserver<IndexedValue<String>>().apply {
            observable.forEachIndexed { index, value -> this.onNext(IndexedValue(index, value)) }
            this.onComplete()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertComplete()
        }
    }

    @Test
    fun isNotEmpty() {
        TestObserver<Boolean>().apply {
            Observable.empty<Int>().isNotEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Observable.just(1).isNotEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertComplete()
        }
    }

    @Test
    fun mapIndexed() {
        val observable = Observable.fromArray("a", "b", "c")
        TestObserver<IndexedValue<String>>().apply {
            observable.mapIndexed { index, value -> IndexedValue(index + 1, value) }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(1, "a"), IndexedValue(2, "b"), IndexedValue(3, "c"))
            this.assertComplete()
        }
    }

    @Test
    fun none() {
        TestObserver<Boolean>().apply {
            Observable.empty<Int>().none().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Observable.just(0).none { it != 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Observable.just(0).none().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Observable.just(0).none { it == 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }
    }


    @Test
    fun orEmpty() {
        TestObserver<Int>().apply {
            (null as Observable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(0)
            this.assertComplete()
        }

        TestObserver<Int>().apply {
            (Observable.empty<Int>() as Observable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(0)
            this.assertComplete()
        }

        TestObserver<Int>().apply {
            (Observable.just(100) as Observable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(100)
            this.assertComplete()
        }
    }

    @Test
    fun reduceIndexed() {

        TestObserver<Int>().apply {
            var indexes = ArrayList<Int>()
            Observable.just(1, 2, 3, 4).reduceIndexed { index, a, b -> indexes.add(index); a + b }.subscribe(this)

            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(10)
            this.assertComplete()

            Assert.assertEquals(listOf(1, 2, 3), indexes.toList())
        }

        TestObserver<Int>().apply {
            var indexes = ArrayList<Int>()
            Observable.just(1, 2, 3, 4).reduceIndexed(0) { index, a, b -> indexes.add(index); a + b }.subscribe(this)

            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(10)
            this.assertComplete()

            Assert.assertEquals(listOf(0, 1, 2, 3), indexes.toList())
        }
    }

    @Test
    fun withIndex() {
        TestObserver<IndexedValue<String>>().apply {
            val observable = Observable.fromArray("a", "b", "c")
            observable.withIndex().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertComplete()
        }
    }
}