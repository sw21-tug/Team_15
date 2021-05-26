package com.simpletrack.model

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.view.TimerFragment
import java.lang.Exception
import kotlin.concurrent.timer

class TimerViewModel(val timerFragment: TimerFragment) {
    // TODO: Implement the ViewModel

    var timerThread = Thread()

    fun startTimerThread() {
        timerThread = Thread(
            Runnable {
                try {
                    while (true) {
                        if (Thread.interrupted()) {
                            return@Runnable
                        }

                        timerFragment.requireActivity().runOnUiThread(
                            Runnable {
                                if (timerFragment.getView() != null) {
                                    timerFragment.requireView().findViewById<TextView>(R.id.timer).text = MainActivity.currentTask!!.getTimeAsString()
                                }
                            }
                        )
                        Thread.sleep(100)
                    }
                } catch (e: Exception) {
                    return@Runnable
                }
            }
        )
    }

    fun startTimer() {

        startTimerThread()

        timerThread.start()

        MainActivity.currentTask = Task("Task ${MainActivity.taskList.size + 1}")
        MainActivity.currentTask!!.startTime()
        timerFragment.getView()?.findViewById<Button>(R.id.stopButton)?.isEnabled = true
        timerFragment.getView()?.findViewById<Button>(R.id.startButton)?.isEnabled = false
    }

    fun continueTimer() {
        startTimerThread()
        timerThread.start()
    }

    fun stopTimer() {
        MainActivity.currentTask!!.stopTime()
        val taskTypes = timerFragment.resources.getStringArray(R.array.TaskTypes)
        if (timerFragment.spinner.selectedItem == taskTypes[taskTypes.size - 1]) {
            MainActivity.currentTask!!.name = timerFragment.requireView().findViewById<EditText>(R.id.customTasknameInput).text.toString()
        } else {
            MainActivity.currentTask!!.name = timerFragment.spinner.selectedItem.toString()
        }
        if (MainActivity.currentTask!!.isStopped()) {
            MainActivity.taskList.add(MainActivity.currentTask!!)
            MainActivity.storage.storeData(MainActivity.taskList)
        }
        timerFragment.getView()?.findViewById<Button>(R.id.startButton)?.isEnabled = true
        timerFragment.getView()?.findViewById<Button>(R.id.stopButton)?.isEnabled = false
        timerThread.interrupt()
    }
}
