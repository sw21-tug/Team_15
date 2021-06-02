package com.simpletrack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.model.TimerButtonState
import com.simpletrack.model.TimerViewModel

class TimerFragment : Fragment() {

    private var viewModel: TimerViewModel? = null

    lateinit var spinner: Spinner

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
        if (viewModel == null) {
            viewModel = TimerViewModel(this)
            view.findViewById<Button>(R.id.stopButton).isEnabled = false
        }
        MainActivity.currentTask?.let {
            if (MainActivity.currentTask!!.running() || MainActivity.currentTask!!.isPaused()) {
                viewModel!!.updateUi()
                viewModel!!.continueTimer()
            }
        }

        val taskTypes = resources.getStringArray(R.array.TaskTypes)

        spinner = view.findViewById<Spinner>(R.id.taskDropdown)
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == taskTypes[taskTypes.size - 1]) {
                    requireView().findViewById<EditText>(R.id.customTasknameInput).visibility = View.VISIBLE
                } else {
                    requireView().findViewById<EditText>(R.id.customTasknameInput).visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        view.findViewById<Button>(R.id.startButton).setOnClickListener {
            viewModel!!.startTimer()
        }

        view.findViewById<Button>(R.id.stopButton).setOnClickListener {
            viewModel!!.stopTimer()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
