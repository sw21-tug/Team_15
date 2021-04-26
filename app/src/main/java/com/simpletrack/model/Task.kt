package com.simpletrack.model

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Task(x: LocalDateTime = LocalDateTime.now(), y: LocalDateTime = LocalDateTime.now(), var name: String = "Taskname") {
    var start: LocalDateTime? = x
        private set
    var stop: LocalDateTime? = y
        private set

    fun startTime() {
        if (start == null) {
            start = LocalDateTime.now()
        }
    }

    fun getDuration(): Duration {
        return Duration.between(start, stop)
    }

    /**
     * returns stopped time in milliseconds
     */
    fun stopTime() {
        stop = LocalDateTime.now()
    }

    fun isStopped(): Boolean {
        return start != null && stop != null
    }

    fun running(): Boolean {
        return start != null && stop == null
    }

    override fun toString(): String {
        return "$name | ${start?.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}\n" +
            "${start?.format(DateTimeFormatter.ofPattern("HH:mm"))}-${stop?.format(DateTimeFormatter.ofPattern("HH:mm"))} | %.1f h".format(getDuration().toMinutes().toDouble() / (60.toDouble()))
    }
}
