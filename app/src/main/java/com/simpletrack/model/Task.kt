package com.simpletrack.model

import java.util.Date

class Task(x: Date = Date(0), y: Date = Date(0)) {
    var start: Date? = x
        private set
    var stop: Date? = y
        private set

    constructor(start_: Date, stop_: Date) : this() {
        start = start_
        stop = stop_
    }

    fun startTime() {
        if (start == null) {
            start = Date()
        }
    }

    fun getTime(): Long {
        return when {
            running() -> Date().time - start!!.time
            isStopped() -> stop!!.time - start!!.time
            else -> 0
        }
    }

    fun getTimeAsString(): String {
        var mils = getTime()
        val s = (mils / 1000).rem(60)
        val m = (mils / (60 * 1000)).rem(60)
        val h = mils / (60 * 60 * 1000)
        return "%02d:%02d:%02d".format(h, m, s)
    }

    /**
     * returns stopped time in milliseconds
     */
    fun stopTime(): Long {
        if (start == null || stop != null) {
            return -1
        }
        stop = Date()

        return stop!!.time - start!!.time
    }

    fun isStopped(): Boolean {
        return start != null && stop != null
    }

    fun running(): Boolean {
        return start != null && stop == null
    }
}
