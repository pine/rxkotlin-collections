package moe.pine.rx.collections

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Test for Observable Extensions
 * Created by pine on 2016/03/21.
 */
class SingleTest {
    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun flatten() {
        val single: Single<Iterable<String>> = Single.just(listOf("a", "b", "c"))
        TestObserver<String>().apply {
            single.flatten().subscribe(this)
            this.assertNoErrors()
            this.assertValueCount(3)
            this.assertValues("a", "b", "c")
            this.assertComplete()
        }
    }
}