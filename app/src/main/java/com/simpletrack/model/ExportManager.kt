package com.simpletrack.model

import android.content.Intent
import android.net.Uri
import com.simpletrack.MainActivity
import java.io.File
import java.io.FileOutputStream

class ExportManager(val activity: MainActivity) {
    fun exportToCSV(taskList: ArrayList<Task>) {

        var export = "Name; Start; End; Duration\n"
        taskList.forEach { task ->
            export += task.toCsv()
        }
        val file = File(activity.filesDir.path + "/simpleTrack.csv")
        val fos = FileOutputStream(file, false)
        fos.write(export.toByteArray())
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "text/csv"
        activity.startActivity(Intent.createChooser(intent, "Share CSV file"))
    }

    fun importCSV(path: File): ArrayList<Task> {
        throw NotImplementedError()
    }
}
