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
    var pauses: MutableList<Pause> = ArrayList()
    var fullPauseTime: Duration = Duration.ZERO
        private set

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
            running() -> Duration.between(start, LocalDateTime.now()) - fullPauseTime
            isPaused() -> Duration.between(start, pauses[pauses.size - 1].start) - fullPauseTime
            isStopped() -> Duration.between(start, stop) - fullPauseTime
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
        if (isPaused()) {
            endPause(stop)
        }
    }

    fun isStopped(): Boolean {
        return start != null && stop != null
    }

    fun isPaused(): Boolean {
        return pauses.size > 0 && pauses[pauses.size - 1].stop == null
    }

    fun running(): Boolean {
        return start != null && stop == null && !isPaused()
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

    fun addPause(pause: Pause) {
        pauses.add(pause)
    }

    fun endPause() {
        pauses[pauses.size - 1].stop = LocalDateTime.now()
        fullPauseTime += pauses[pauses.size - 1].getPauseTime()
    }

    fun endPause(end: LocalDateTime?) {
        pauses[pauses.size - 1].stop = end
        fullPauseTime += Duration.between(pauses[pauses.size - 1].start, end)
    }
}

class Pause(var name: String = "Pausename") : Serializable {
    var start: LocalDateTime? = null
        set
    var stop: LocalDateTime? = null
        set

    constructor(start_: LocalDateTime) : this() {
        start = start_
    }

    fun getPauseTime(): Duration {
        if (start == null || stop == null) {
            return Duration.ZERO
        }
        return Duration.between(start, stop)
    }
}
