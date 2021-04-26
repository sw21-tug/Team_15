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
import java.util.Date
import kotlin.collections.ArrayList

class ViewListFragment : Fragment() {

    companion object {
        fun newInstance() = ViewListFragment()
    }

    private lateinit var viewModel: ViewListViewModel

    private lateinit var listView: ListView

    private var test_list = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.view_list_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.listView)
        test_list.add(Task(Date(1), Date(3)))
        test_list.add(Task(Date(1), Date(3)))
        test_list.add(Task(Date(1), Date(3)))
        test_list.add(Task(Date(1), Date(3)))

        val list_adap = ListAdapter(this.activity as Activity, test_list)
        listView.adapter = list_adap
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
