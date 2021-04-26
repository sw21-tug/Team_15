package com.simpletrack

import com.simpletrack.model.Task
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Thread.sleep

class TaskTests {
    @Test
    fun timeCount_isCorrect() {
        val sleepTime: Long = 1000
        val epsilon: Long = 20
        val task = Task()

        task.startTime()
        sleep(sleepTime)
        task.stopTime()

        assert(task.getDuration().toMillis() >= sleepTime - epsilon && task.getDuration().toMillis() <= sleepTime + epsilon)
    }

    @Test
    fun stop_before_start() {
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

    // stop gets disabled when pressed this test makes no sense
   /* @Test
    fun stop_twice() {
        val task = Task()

        task.startTime()
        sleep(5)
        task.stopTime()
        val firststop = task.stop

        sleep(10)
        task.stopTime()

        assertEquals(firststop, task.stop)
    }*/

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
