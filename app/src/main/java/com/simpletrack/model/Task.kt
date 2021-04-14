import java.util.*

class Task() {
    var start : Date? =  null
            get
            private set
    var stop : Date? = null
            get
            private set

    fun startTime() {
        start = Date()
    }

    // returns stopped time in milliseconds
    fun stopTime(): Long {
        if (start == null) {
            return -1
        }
        stop = Date()

        return stop!!.time - start!!.time
    }



}