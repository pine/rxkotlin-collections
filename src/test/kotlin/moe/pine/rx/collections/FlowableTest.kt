package moe.pine.rx.collections

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
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
}