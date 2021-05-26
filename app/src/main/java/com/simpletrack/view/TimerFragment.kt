package com.simpletrack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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

        val taskTypes = resources.getStringArray(R.array.TaskTypes)

        val spinner = view.findViewById<Spinner>(R.id.taskDropdown)
        if (spinner != null) {
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item, taskTypes
                )
            }
            spinner.adapter = adapter

            if (adapter != null) {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == taskTypes[taskTypes.size - 1]) {
                    requireView().findViewById<EditText>(R.id.customTasknameInput).visibility = View.VISIBLE
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        view.findViewById<Button>(R.id.startButton).setOnClickListener {
            timerThread = Thread(
                Runnable {
                    try {
                        while (true) {
                            if (Thread.interrupted()) {
                                return@Runnable
                            }

                            this@TimerFragment.requireActivity().runOnUiThread {
                                if (getView() != null) {
                                    requireView().findViewById<TextView>(R.id.timer).text =
                                        MainActivity.currentTask!!.getTimeAsString()
                                }
                            }
                            Thread.sleep(100)
                        }
                    } catch (e: Exception) {
                        return@Runnable
                    }
                }
            )

            timerThread.start()

            MainActivity.currentTask = Task("Task ${MainActivity.taskList.size + 1}")
            MainActivity.currentTask!!.startTime()
            view.findViewById<Button>(R.id.stopButton).isEnabled = true
            view.findViewById<Button>(R.id.startButton).isEnabled = false
        }

        view.findViewById<Button>(R.id.stopButton).setOnClickListener {
            MainActivity.currentTask!!.stopTime()
            MainActivity.currentTask!!.name = spinner.selectedItem.toString()
            if (MainActivity.currentTask!!.isStopped()) {
                MainActivity.taskList.add(MainActivity.currentTask!!)
                MainActivity.storage.storeData(MainActivity.taskList)
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
