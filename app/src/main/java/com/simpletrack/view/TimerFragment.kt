package com.simpletrack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.model.Task
import com.simpletrack.model.TimerViewModel
import java.lang.Exception

class TimerFragment : Fragment() {

    var timerThread = Thread()

    private lateinit var viewModel: TimerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.timer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.stopButton).isEnabled = false

        view.findViewById<Button>(R.id.startButton).setOnClickListener {
            timerThread = Thread(
                Runnable {
                    try {
                        while (true) {
                            if (Thread.interrupted()) {
                                return@Runnable
                            }

                            this@TimerFragment.requireActivity().runOnUiThread(
                                java.lang.Runnable {
                                    if (getView() != null) {
                                        requireView().findViewById<TextView>(R.id.timer).text = MainActivity.currentTask!!.getTimeAsString()
                                    }
                                }
                            )
                            Thread.sleep(1000)
                        }
                    } catch (e: Exception) {
                        return@Runnable
                    }
                }
            )

            timerThread.start()

            MainActivity.currentTask = Task()
            MainActivity.currentTask!!.startTime()
            view.findViewById<Button>(R.id.stopButton).isEnabled = true
            view.findViewById<Button>(R.id.startButton).isEnabled = false
        }

        view.findViewById<Button>(R.id.stopButton).setOnClickListener {
            MainActivity.currentTask!!.stopTime()
            if(MainActivity.currentTask!!.isStopped()) {
                MainActivity.taskList.add(MainActivity.currentTask!!)
            }
            view.findViewById<Button>(R.id.startButton).isEnabled = true
            view.findViewById<Button>(R.id.stopButton).isEnabled = false
            timerThread.interrupt()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
    }

    override fun onDetach() {
        timerThread.interrupt()
        super.onDetach()
    }
}
