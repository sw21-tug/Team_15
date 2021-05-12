package com.simpletrack.model

import java.io.File

class ExportManager {
    companion object {
        fun exportToCSV(taskList: ArrayList<Task>) {
            throw NotImplementedError()
        }

        fun importCSV(path: File): ArrayList<Task> {
            throw NotImplementedError()
        }
    }
}
