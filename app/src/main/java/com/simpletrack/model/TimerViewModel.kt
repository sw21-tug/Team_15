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

                        if (buttonState == TimerButtonState.PAUSE) {
                            timerFragment.requireActivity().runOnUiThread(
                                Runnable {
                                    if (timerFragment.view != null) {
                                        timerFragment.requireView().findViewById<TextView>(R.id.timer).text = MainActivity.currentTask!!.getTimeAsString()
                                    }
                                }
                            )
                        }
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
                buttonState = TimerButtonState.PAUSE
                updateUi()
            }
            TimerButtonState.PAUSE -> {
                MainActivity.currentTask?.addPause(Pause(LocalDateTime.now()))
                buttonState = TimerButtonState.CONTINUE
                updateUi()
            }
            TimerButtonState.CONTINUE -> {
                MainActivity.currentTask?.endPause()
                buttonState = TimerButtonState.PAUSE
                updateUi()
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
        timerFragment.getView()?.findViewById<Button>(R.id.startButton)?.setText(R.string.start)
        timerFragment.getView()?.findViewById<Button>(R.id.stopButton)?.isEnabled = false
        buttonState = TimerButtonState.START
        timerThread.interrupt()
    }

    fun updateUi() {
        val startButton = timerFragment.view?.findViewById<Button>(R.id.startButton) as TextView
        val stopButton = timerFragment.view?.findViewById<Button>(R.id.stopButton) as TextView

        startButton.isEnabled = true
        when (buttonState) {
            TimerButtonState.START -> {
                startButton.setText(R.string.start)
                stopButton.isEnabled = false
            }
            TimerButtonState.PAUSE -> {
                startButton.setText(R.string.pause)
                stopButton.isEnabled = true
            }
            TimerButtonState.CONTINUE -> {
                startButton.setText(R.string.Continue)
                stopButton.isEnabled = true
                timerFragment.requireView().findViewById<TextView>(R.id.timer).text = MainActivity.currentTask!!.getTimeAsString()
            }
        }
    }
}
