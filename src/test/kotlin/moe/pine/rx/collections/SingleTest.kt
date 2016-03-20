package moe.pine.rx.collections

import org.junit.After
import org.junit.Before
import org.junit.Test
import rx.Single
import rx.observers.TestSubscriber

/**
 * Test for Observable Extensions
 * Created by pine on 2016/03/21.
 */
class SingleTestTest {
    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun flatten() {
        val single: Single<Iterable<String>> = Single.just(listOf("a", "b", "c"))
        TestSubscriber<String>().apply {
            single.flatten().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues("a", "b", "c")
            this.assertCompleted()
        }
    }

    @Test
    fun requireNoNulls() {
        TestSubscriber<Int>().apply {
            Single.just(1).requireNoNulls().subscribe(this)
            this.assertValues(1)
            this.assertCompleted()
        }

        TestSubscriber<Int>().apply {
            Single.just<Int?>(null).requireNoNulls().subscribe(this)
            this.assertValueCount(0)
            this.assertError(IllegalArgumentException::class.java)
            this.assertNotCompleted()
        }
    }
}