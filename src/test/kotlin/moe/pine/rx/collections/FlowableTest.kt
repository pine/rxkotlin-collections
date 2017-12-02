package moe.pine.rx.collections

import io.reactivex.Flowable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


/**
 * Test for Flowable Extensions
 * Created by pine on 2017/11/29.
 */
class FlowableTest {
    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun filterIndexed() {
        val flowable: Flowable<String> = Flowable.fromArray("a", "b", "c")
        TestSubscriber<String>().apply {
            flowable.filterIndexed { i, s -> i == 0 || s == "c" }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues("a", "c")
            this.assertComplete()
        }
    }

    @Test
    fun filterIsInstance() {
        val flowable: Flowable<Any> = Flowable.just(1, false, "a")
        TestSubscriber<Boolean>().apply {
            flowable.filterIsInstance<Boolean>().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }

        TestSubscriber<String>().apply {
            flowable.filterIsInstance(String::class.java).subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue("a")
            this.assertComplete()
        }
    }

    @Test
    fun filterNot() {
        val flowable: Flowable<Int> = Flowable.fromArray(1, 2, 3, 4)

        TestSubscriber<Int>().apply {
            flowable.filterNot { it % 2 == 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(2)
            this.assertValues(1, 3)
            this.assertComplete()
        }
    }

    @Test
    fun flatten() {
        val flowable: Flowable<Iterable<String>> = Flowable.just(listOf("a", "b", "c"))
        TestSubscriber<String>().apply {
            flowable.flatten().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues("a", "b", "c")
            this.assertComplete()
        }
    }

    @Test
    fun forEachIndexed() {
        val flowable: Flowable<String> = Flowable.fromArray("a", "b", "c")
        TestObserver<IndexedValue<String>>().apply {
            flowable.forEachIndexed { index, value -> this.onNext(IndexedValue(index, value)) }
            this.onComplete()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertComplete()
        }
    }

    @Test
    fun isNotEmpty() {
        TestObserver<Boolean>().apply {
            Flowable.empty<Int>().isNotEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Flowable.just(1).isNotEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertComplete()
        }
    }

    @Test
    fun mapIndexed() {
        val flowable = Flowable.fromArray("a", "b", "c")
        TestSubscriber<IndexedValue<String>>().apply {
            flowable.mapIndexed { index, value -> IndexedValue(index + 1, value) }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(1, "a"), IndexedValue(2, "b"), IndexedValue(3, "c"))
            this.assertComplete()
        }
    }

    @Test
    fun none() {
        TestObserver<Boolean>().apply {
            Flowable.empty<Int>().none().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Flowable.just(0).none { it != 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(true)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Flowable.just(0).none().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }

        TestObserver<Boolean>().apply {
            Flowable.just(0).none { it == 0 }.subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(false)
            this.assertComplete()
        }
    }

    @Test
    fun orEmpty() {
        TestSubscriber<Int>().apply {
            (null as Flowable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(0)
            this.assertComplete()
        }

        TestSubscriber<Int>().apply {
            (Flowable.empty<Int>() as Flowable<Int>?).orEmpty().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(0)
            this.assertComplete()
        }

        TestSubscriber<Int>().apply {
            (Flowable.just(100) as Flowable<Int>?).orEmpty().subscribe(this)
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
            Flowable.just(1, 2, 3, 4).reduceIndexed { index, a, b -> indexes.add(index); a + b }.subscribe(this)

            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(10)
            this.assertComplete()

            Assert.assertEquals(listOf(1, 2, 3), indexes.toList())
        }

        TestObserver<Int>().apply {
            var indexes = ArrayList<Int>()
            Flowable.just(1, 2, 3, 4).reduceIndexed(0) { index, a, b -> indexes.add(index); a + b }.subscribe(this)

            this.assertNoErrors()
            this.assertValueCount(1)
            this.assertValue(10)
            this.assertComplete()

            Assert.assertEquals(listOf(0, 1, 2, 3), indexes.toList())
        }
    }

    @Test
    fun withIndex() {
        TestSubscriber<IndexedValue<String>>().apply {
            val flowable = Flowable.fromArray("a", "b", "c")
            flowable.withIndex().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues(IndexedValue(0, "a"), IndexedValue(1, "b"), IndexedValue(2, "c"))
            this.assertComplete()
        }
    }
}