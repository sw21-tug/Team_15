package com.simpletrack

import org.junit.Test

import org.junit.Assert.*
import java.lang.Thread.sleep

import Task

class TaskTests {
    
    @Test
    fun timeCount_isCorrect(){
        val sleepTime : Long = 100
        val task = Task()

        task.startTime()
        sleep(sleepTime)
        val retval = task.stopTime()

        assertEquals(sleepTime, retval)

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
        var firststart = task.start
        sleep(5)
        task.startTime()

        assertEquals(firststart, task.start)
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