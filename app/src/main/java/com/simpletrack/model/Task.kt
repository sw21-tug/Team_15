import java.util.Date

class Task() {
    var start: Date? = null
        get
        private set
    var stop: Date? = null
        get
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
        return -1
    }

    fun getTimeAsString(): String {
        return ""
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
