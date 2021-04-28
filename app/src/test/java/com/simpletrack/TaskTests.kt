package com.simpletrack

import com.simpletrack.model.Task
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Thread.sleep
import java.time.LocalDateTime

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

        task.stopTime()

        assertEquals(null, task.stop)
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

    @Test
    fun getTimeNotStarted() {
        val task = Task()
        assertEquals(0, task.getDuration().toMillis())
    }

    @Test
    fun getTimeAsStringCorrect() {
        val task = Task(LocalDateTime.of(2000, 10, 10, 20, 10, 10), LocalDateTime.of(2000, 10, 10, 21, 11, 20))
        assertEquals("01:01:10", task.getTimeAsString())
    }

    @Test
    fun getTimeAsStringThreeDigitHours() {
        val task = Task(LocalDateTime.of(2000, 10, 10, 20, 10, 10), LocalDateTime.of(2000, 10, 18, 20, 10, 10))
        assertEquals("192:00:00", task.getTimeAsString())
    }

    @Test
    fun getTimeAsStringNotStarted() {
        val task = Task()
        assertEquals("00:00:00", task.getTimeAsString())
    }
}
