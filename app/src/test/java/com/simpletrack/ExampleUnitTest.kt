package com.simpletrack

import org.junit.Test

import org.junit.Assert.*
import java.lang.Thread.sleep

import Task

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
        val sleepTime : Long = 100
        val task = Task()

        task.startTime()
        sleep(sleepTime)
        val retval = task.stopTime()

        assertEquals(retval, sleepTime)


    }
}