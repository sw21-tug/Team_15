package com.simpletrack.model

import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Task(var name: String = "Taskname") : Serializable {
    var start: LocalDateTime? = null
        private set
    var stop: LocalDateTime? = null
        private set
    var pauses = ArrayList<Pause>()

    constructor(start_: LocalDateTime, stop_: LocalDateTime, name_: String = "Taskname") : this() {
        start = start_
        stop = stop_
        name = name_
    }

    fun setNewName(name_: String) {
        name = name_
    }

    fun startTime() {
        if (start == null) {
            start = LocalDateTime.now()
        }
    }

    fun getTimeAsString(): String {
        val mils = getDuration().toMillis()
        val s = (mils / 1000).rem(60)
        val m = (mils / (60 * 1000)).rem(60)
        val h = mils / (60 * 60 * 1000)
        return "%02d:%02d:%02d".format(h, m, s)
    }

    fun getDuration(): Duration {
        return when {
            running() -> Duration.between(start, LocalDateTime.now())
            isStopped() -> Duration.between(start, stop)
            else -> Duration.ZERO
        }
    }

    /**
     * returns stopped time in milliseconds
     */
    fun stopTime() {
        if (!running())
            return
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

    /**
     * Name; Startdate; EndDate; Duration
     */
    fun toCsv(): String {
        if (isStopped())
            return "$name; ${start?.toString()}; ${stop?.toString()}; ${getTimeAsString()}\n"
        return ""
    }

    fun getFullPauseTime(): Duration {
        return Duration.ZERO
    }
}

class Pause(var name: String = "Pausename") : Serializable {
    var start: LocalDateTime? = null
        private set
    var stop: LocalDateTime? = null
        private set

    constructor(start_: LocalDateTime) : this() {
        start = start_
    }

    fun getPauseTime(): Duration {
        return Duration.ZERO
    }
}
