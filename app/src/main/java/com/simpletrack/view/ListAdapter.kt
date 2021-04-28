package com.simpletrack.view

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.simpletrack.R
import com.simpletrack.model.Task

class ListAdapter(private val context: Activity, private val task_list: ArrayList<Task>) :
    ArrayAdapter<Task>(context, R.layout.list_layout, task_list) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_layout, null, true)

        val titleText = rowView.findViewById(R.id.taskName) as TextView
        titleText.text = task_list[position].toString()
        return rowView
    }
}
