package com.simpletrack.model

import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class ExportManager {
    companion object {
        fun exportToCSV(taskList: ArrayList<Task>) {
            var export = "Name; Start; End; Duration\n"
            taskList.forEach { task ->
                export += task.toCsv()
            }
            val file = File("${Environment.DIRECTORY_DOCUMENTS}/simpleTrack.csv")
            val fos = FileOutputStream(file, false)
            fos.write(export.toByteArray())
        }

        fun importCSV(path: File): ArrayList<Task> {
            throw NotImplementedError()
        }
    }
}
