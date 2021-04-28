package com.simpletrack.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    private var test_list = arrayListOf(
        Task(LocalDateTime.of(2021, 3, 3, 10, 10, 45), LocalDateTime.of(2021, 3, 3, 12, 34, 50), "Eating"),
        Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 3, 7, 12, 34, 50), "PARTYING"),
        Task(LocalDateTime.of(2021, 1, 13, 15, 16, 0), LocalDateTime.of(2021, 1, 20, 13, 45, 50), "Studying"),
        Task(LocalDateTime.of(2020, 12, 3, 0, 0, 45), LocalDateTime.of(2020, 12, 30, 7, 34, 50), "Programming"),
        Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 5, 7, 12, 34, 50), "LOCKDOWNING")
    )

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

        val list_adap = ListAdapter(this.activity as Activity, test_list)
        listView.adapter = list_adap
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
