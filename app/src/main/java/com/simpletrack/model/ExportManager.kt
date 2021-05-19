package com.simpletrack.model

import android.content.Intent
import androidx.core.content.FileProvider
import com.simpletrack.MainActivity
import java.io.File

class ExportManager(val activity: MainActivity) {
    fun exportToCSV(taskList: ArrayList<Task>) {

        var export = "Name; Start; End; Duration\n"
        taskList.forEach { task ->
            export += task.toCsv()
        }
        val temp = File.createTempFile("simpleTrack", ".csv", activity.cacheDir)
        temp.writeText(export)

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this.activity, activity.packageName + ".fileprovider", temp))
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "text/csv"
        activity.startActivity(Intent.createChooser(intent, "Share CSV file"))
    }

    fun importCSV(path: File): ArrayList<Task> {
        throw NotImplementedError()
    }
}
