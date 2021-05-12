package com.simpletrack

import android.content.Context
import android.os.Environment
import com.simpletrack.model.ExportManager
import com.simpletrack.model.Task
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.lang.Exception
import java.time.LocalDateTime

class ExportTest {
    @Test
    fun exportData() {
        val taskList = arrayListOf(
            Task(LocalDateTime.of(2021, 3, 3, 10, 10, 45), LocalDateTime.of(2021, 3, 3, 12, 34, 50), "Eating"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 3, 7, 12, 34, 50), "PARTYING"),
            Task(LocalDateTime.of(2021, 1, 13, 15, 16, 0), LocalDateTime.of(2021, 1, 20, 13, 45, 50), "Studying"),
            Task(LocalDateTime.of(2020, 12, 3, 0, 0, 45), LocalDateTime.of(2020, 12, 30, 7, 34, 50), "Programming"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 5, 7, 12, 34, 50), "LOCKDOWNING")
        )
        try {
            ExportManager.exportToCSV(taskList)
            val file = File("${Environment.DIRECTORY_DOCUMENTS}/simpleTrack.csv")
            val resultList = ExportManager.importCSV(file)
            assertEquals(taskList.size, resultList.size)
        } catch (e: Exception) {
            assert(false)
        }
    }
}
