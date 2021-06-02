package com.simpletrack.model

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.view.TimerFragment
import java.lang.Exception
import java.time.LocalDateTime
import kotlin.concurrent.timer

enum class TimerButtonState {
    START, PAUSE, CONTINUE
}

class TimerViewModel(val timerFragment: TimerFragment) {
    // TODO: Implement the ViewModel

    var timerThread = Thread()
    var buttonState: TimerButtonState = TimerButtonState.START

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
                                if (timerFragment.view != null) {
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

        val startButton = timerFragment.view?.findViewById<Button>(R.id.startButton) as TextView

        when (buttonState) {
            TimerButtonState.START -> {
                startTimerThread()

                timerThread.start()

                MainActivity.currentTask = Task("Task ${MainActivity.taskList.size + 1}")
                MainActivity.currentTask!!.startTime()
                timerFragment.view?.findViewById<Button>(R.id.stopButton)?.isEnabled = true
                startButton.isEnabled = true
                startButton.text = R.string.pause.toString()
                buttonState = TimerButtonState.PAUSE
            }
            TimerButtonState.PAUSE -> {
                // stop timer
                timerThread.interrupt()
                MainActivity.currentTask?.addPause(Pause(LocalDateTime.now()))
                startButton.text = R.string.Continue.toString()
                buttonState = TimerButtonState.CONTINUE
            }
            TimerButtonState.CONTINUE -> {
                // start timer
                startTimerThread()
                MainActivity.currentTask?.endPause()
                startButton.text = R.string.pause.toString()
                buttonState = TimerButtonState.PAUSE
            }
        }
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
