package com.simpletrack.view

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.simpletrack.R
import com.simpletrack.model.Pause
import java.time.format.DateTimeFormatter

class PauseAdapter(private val context: Activity, private val pause_list: ArrayList<Pause>) :
    ArrayAdapter<Pause>(context, R.layout.list_layout, pause_list) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_layout, null, true)

        val titleText = rowView.findViewById(R.id.taskName) as TextView
        val durationText = rowView.findViewById(R.id.duration) as TextView
        val startDateText = rowView.findViewById(R.id.startDate) as TextView
        val startTimeText = rowView.findViewById(R.id.startTime) as TextView
        val endDateText = rowView.findViewById(R.id.endDate) as TextView
        val endTimeText = rowView.findViewById(R.id.endTime) as TextView

        titleText.text = pause_list[position].name
        durationText.text = "%.1f h".format(pause_list[position].getPauseTime().toMinutes().toDouble() / (60.toDouble()))
        startDateText.text = pause_list[position].start?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        startTimeText.text = pause_list[position].start?.format(DateTimeFormatter.ofPattern("HH:mm"))
        endDateText.text = pause_list[position].stop?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        endTimeText.text = pause_list[position].stop?.format(DateTimeFormatter.ofPattern("HH:mm"))
        return rowView
    }
}
