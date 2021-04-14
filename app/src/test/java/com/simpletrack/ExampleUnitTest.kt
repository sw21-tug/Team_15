package com.simpletrack

import org.junit.Test

import org.junit.Assert.*

import Task.kt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun timeCount_isCorrect(){
        val task : Task

        task.start()
        sleep(10)
        val retval = task.stop()

        assertEquals(retval, 10)


    }
}