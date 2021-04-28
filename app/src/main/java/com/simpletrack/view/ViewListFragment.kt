package com.simpletrack.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.model.Task
import com.simpletrack.model.ViewListViewModel
import java.time.LocalDateTime

class ViewListFragment : Fragment() {

    companion object {
        fun newInstance() = ViewListFragment()
    }

    private lateinit var viewModel: ViewListViewModel

    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.listView)

        val listAdapter = ListAdapter(this.activity as Activity, MainActivity.taskList)
        listView.adapter = listAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
