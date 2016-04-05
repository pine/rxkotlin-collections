package moe.pine.rx.collections

import org.junit.After
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.observers.TestSubscriber
import rx.subjects.PublishSubject
import java.util.*
import kotlin.test.assertEquals

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
        val observable: Observable<String> = Observable.from(listOf("a", "b", "c"))
        TestSubscriber<String>().apply {
            observable.filterIndexed { i, s -> i == 0 || s == "c" }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues("a", "c")
            this.assertCompleted()
        }
    }

    @Test
    fun filterIsInstance() {
        val observable: Observable<Any> = Observable.just(1, false, "a")
        TestSubscriber<Boolean>().apply {
            observable.filterIsInstance<Boolean>().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
        }

        TestSubscriber<String>().apply {
            observable.filterIsInstance(String::class.java).subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue("a")
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
    fun firstOrNull() {
        TestSubscriber<Int?>().apply {
            val observable = Observable.just(1024, 2048)
            observable.firstOrNull().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValues(1024)
            this.assertCompleted()
        }

        TestSubscriber<Int?>().apply {
            val observable = Observable.empty<Int>()
            observable.firstOrNull().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValues(null)
            this.assertCompleted()
        }

        TestSubscriber<Int?>().apply {
            val observable = Observable.just("a", "b")
            observable.firstOrNull { it.contains("b") }.map { it?.length }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValues(1)
            this.assertCompleted()
        }

        TestSubscriber<Int?>().apply {
            val observable = Observable.just(0, 1, 2)
            observable.firstOrNull { it > 2 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValues(null)
            this.assertCompleted()
        }
    }

    @Test
    fun flatten() {
        val observable: Observable<Iterable<String>> = Observable.just(listOf("a", "b", "c"))
        TestSubscriber<String>().apply {
            observable.flatten().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues("a", "b", "c")
            this.assertCompleted()
        }
    }

    @Test
    fun forEachIndexed() {
        val observable: Observable<String> = Observable.from(listOf("a", "b", "c"))
        TestSubscriber<IndexedValue<String>>().apply {
            observable.forEachIndexed { index, value -> this.onNext(IndexedValue(index, value)) }
            this.onCompleted()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertCompleted()
        }
    }

    @Test
    fun isNotEmpty() {
        TestSubscriber<Boolean>().apply {
            Observable.empty<Int>().isNotEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertCompleted()
        }

        TestSubscriber<Boolean>().apply {
            Observable.just(1).isNotEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertCompleted()
        }
    }

    @Test
    fun mapIndexed() {
        val observable = Observable.from(listOf("a", "b", "c"))
        TestSubscriber<IndexedValue<String>>().apply {
            observable.mapIndexed { index, value -> IndexedValue(index + 1, value) }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(1, "a"), IndexedValue(2, "b"), IndexedValue(3, "c"))
            this.assertCompleted()
        }
    }

    @Test
    fun mapIndexedNotNull() {
        val observable = Observable.from(listOf("a", "b", "c"))
        TestSubscriber<String>().apply {
            observable.mapIndexedNotNull { index, value -> if (index % 2 == 0) value else null }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues("a", "c")
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
    fun orEmpty() {
        TestSubscriber<Int>().apply {
            (null as Observable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(0)
            this.assertCompleted()
        }

        TestSubscriber<Int>().apply {
            (Observable.empty<Int>() as Observable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(0)
            this.assertCompleted()
        }

        TestSubscriber<Int>().apply {
            (Observable.just(100) as Observable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(100)
            this.assertCompleted()
        }
    }

    @Test
    fun reduceIndexed() {
        TestSubscriber<Int>().apply {
            var indexes = ArrayList<Int>()
            Observable.just(1, 2, 3, 4).reduceIndexed { index, a, b -> indexes.add(index); a + b }.subscribe(this)

            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(10)
            this.assertCompleted()

            assertEquals(listOf(1, 2, 3), indexes.toList())
        }

        TestSubscriber<Int>().apply {
            var indexes = ArrayList<Int>()
            Observable.just(1, 2, 3, 4).reduceIndexed(0) { index, a, b -> indexes.add(index); a + b }.subscribe(this)

            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(10)
            this.assertCompleted()

            assertEquals(listOf(0, 1, 2, 3), indexes.toList())
        }
    }

    @Test
    fun requireNoNulls() {
        TestSubscriber<Int>().apply {
            Observable.just(1, null).requireNoNulls().subscribe(this)
            this.assertValueCount(1)
            this.assertValue(1)
            this.assertError(IllegalArgumentException::class.java)
            this.assertNotCompleted()
        }
    }

    @Test
    fun withIndex() {
        TestSubscriber<IndexedValue<String>>().apply {
            val observable = Observable.from(listOf("a", "b", "c"))
            observable.withIndex().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertCompleted()
        }

        TestSubscriber<IndexedValue<String>>().apply {
            val observable1 = PublishSubject.create<String>()
            val observable2 = PublishSubject.create<String>()
            observable1.withIndex().subscribe(this)
            observable2.withIndex().subscribe(this)

            observable1.onNext("a")
            observable2.onNext("b")

            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(0, "b"))
        }
    }
}
