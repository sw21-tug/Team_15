package com.simpletrack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.model.SettingsViewModel

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.exportButton).setOnClickListener {
            MainActivity.exportManager.exportToCSV(MainActivity.taskList)
        }

        view.findViewById<Button>(R.id.resetButton).setOnClickListener {
            val act = activity as MainActivity
            act.openResetDataDialogue()
        }
    }
}
