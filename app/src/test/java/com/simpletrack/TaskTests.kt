package com.simpletrack

import org.junit.Test
import org.junit.Assert.assertEquals
import java.lang.Thread.sleep

import Task

class TaskTests {
    @Test
    fun timeCount_isCorrect() {
        val sleepTime: Long = 1000
        val epsilon: Long = 10
        val task = Task()

        task.startTime()
        sleep(sleepTime)
        val retval = task.stopTime()

        assert(retval >= sleepTime - epsilon && retval <= sleepTime + epsilon)
    }

    @Test
    fun stop_before_start(){
        val task = Task()

        val retval = task.stopTime()

        assertEquals(-1, retval)
    }

    @Test
    fun start_twice() {
        val task = Task()

        task.startTime()
        val firststart = task.start
        sleep(5)
        task.startTime()

        assertEquals(firststart, task.start)
    }

    @Test
    fun stop_twice() {
        val task = Task()

        task.startTime()
        sleep(5)
        task.stopTime()
        val firststop = task.stop

        sleep(10)
        task.stopTime()

        assertEquals(firststop, task.stop)
    }

    @Test
    fun isRunningWhileRunning() {
        val task = Task()
        task.startTime()
        assert(task.running())
    }
    @Test
    fun isRunningWhileStopped() {
        val task = Task()
        task.startTime()
        task.stopTime()
        assert(!task.running())
    }
    @Test
    fun isRunningBeforeStart() {
        val task = Task()
        assert(!task.running())
    }

    @Test
    fun isStoppedWhileRunning() {
        val task = Task()
        task.startTime()
        assert(!task.isStopped())
    }
    @Test
    fun isStoppedWhileStopped() {
        val task = Task()
        task.startTime()
        task.stopTime()
        assert(task.isStopped())
    }
    @Test
    fun isStoppedBeforeStart() {
        val task = Task()
        assert(!task.isStopped())
    }
}