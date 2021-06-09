package com.simpletrack

import com.simpletrack.model.Pause
import com.simpletrack.model.Task
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Thread.sleep
import java.time.LocalDateTime

class TaskTests {

    @Test
    fun test_full_pause_time() {
        val sleepTime: Long = 1000
        val epsilon: Long = 20
        val task = Task()

        task.startTime()
        task.addPause(Pause(LocalDateTime.now()))
        sleep(sleepTime)
        task.endPause()
        task.addPause(Pause(LocalDateTime.now()))
        sleep(sleepTime)
        task.endPause()
        task.addPause(Pause(LocalDateTime.now()))
        sleep(sleepTime)
        task.endPause()
        task.stopTime()

        assert(task.fullPauseTime.toMillis() >= 3 * sleepTime - epsilon && task.fullPauseTime.toMillis() <= 3 * sleepTime + epsilon)
    }

    @Test
    fun get_pause_time_test() {
        val sleepTime: Long = 1000
        val epsilon: Long = 20
        val task = Task()

        val pause = Pause(LocalDateTime.now())
        task.addPause(pause)
        sleep(sleepTime)
        task.endPause()

        assert(pause.getPauseTime().toMillis() >= sleepTime - epsilon && pause.getPauseTime().toMillis() <= sleepTime + epsilon)
    }

    @Test
    fun pause_time_correct() {
        val sleepTime: Long = 1000
        val epsilon: Long = 20
        val task = Task()

        task.startTime()
        sleep(sleepTime)
        task.addPause(Pause(LocalDateTime.now()))
        sleep(sleepTime)
        task.endPause()
        task.stopTime()

        assert(task.getDuration().toMillis() >= sleepTime - epsilon && task.getDuration().toMillis() <= sleepTime + epsilon)
    }

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

    @Test
    fun testToCsv() {
        val start = LocalDateTime.of(2021, 5, 12, 8, 46, 0)
        val stop = LocalDateTime.of(2021, 5, 12, 9, 46, 0)
        val task = Task(start, stop, "TestTask")
        // Name; Startdate; EndDate; Duration
        assertEquals("TestTask; 2021-05-12T08:46; 2021-05-12T09:46; 01:00:00\n", task.toCsv())
    }

    @Test
    fun testToCsvEdgeCase() {
        val task = Task()
        assertEquals("", task.toCsv())
    }
}
