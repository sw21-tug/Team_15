package com.simpletrack.view

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.simpletrack.R
import com.simpletrack.model.Pause
import com.simpletrack.model.Task
import java.time.format.DateTimeFormatter

class TaskPopup(val activity: FragmentActivity, val view: View, val task: Task, val listAdapter: ListAdapter) {

    fun display() {
        val popupView = LayoutInflater.from(activity).inflate(R.layout.task_detail_fragment, null)
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        val titleText = popupView.findViewById<TextView>(R.id.taskName)
        val durationText = popupView.findViewById<TextView>(R.id.duration)
        val startDateText = popupView.findViewById<TextView>(R.id.startDate)
        val startTimeText = popupView.findViewById<TextView>(R.id.startTime)
        val endDateText = popupView.findViewById<TextView>(R.id.endDate)
        val endTimeText = popupView.findViewById<TextView>(R.id.endTime)
        val sumText = popupView.findViewById<TextView>(R.id.sum)

        // Pause
        val duration2Text = popupView.findViewById<TextView>(R.id.duration2)
        val listView = popupView.findViewById<ListView>(R.id.pauseList)
        listView.adapter = PauseAdapter(this.activity as Activity, task.pauses as ArrayList<Pause>)

        val deleteButton = popupView.findViewById<Button>(R.id.button_delete)
        val closeButton = popupView.findViewById<Button>(R.id.button_close)

        titleText.text = task.name
        durationText.text = "%.1f h".format(task.getDuration().toMinutes().toDouble() / (60.toDouble()))
        startDateText.text = task.start?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        startTimeText.text = task.start?.format(DateTimeFormatter.ofPattern("HH:mm"))
        endDateText.text = task.stop?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        endTimeText.text = task.stop?.format(DateTimeFormatter.ofPattern("HH:mm"))
        sumText.text = "%.1f h".format(task.getDuration().plus(task.fullPauseTime).toMinutes().toDouble() / (60.toDouble()))

        duration2Text.text = "%.1f h".format(task.fullPauseTime.toMinutes().toDouble() / (60.toDouble()))

        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }

        deleteButton.setOnClickListener {
            listAdapter.remove(task)
            popupWindow.dismiss()
        }
    }
}
