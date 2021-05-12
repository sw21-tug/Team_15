package com.simpletrack

import android.os.Environment
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.simpletrack.model.Task
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.lang.Exception
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class ExportTest {
    @Test
    fun exportData() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val exportManager = MainActivity.exportManager
        val taskList = arrayListOf(
            Task(LocalDateTime.of(2021, 3, 3, 10, 10, 45), LocalDateTime.of(2021, 3, 3, 12, 34, 50), "Eating"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 3, 7, 12, 34, 50), "PARTYING"),
            Task(LocalDateTime.of(2021, 1, 13, 15, 16, 0), LocalDateTime.of(2021, 1, 20, 13, 45, 50), "Studying"),
            Task(LocalDateTime.of(2020, 12, 3, 0, 0, 45), LocalDateTime.of(2020, 12, 30, 7, 34, 50), "Programming"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 5, 7, 12, 34, 50), "LOCKDOWNING")
        )
        try {
            exportManager.exportToCSV(taskList)
            val file = File("${Environment.DIRECTORY_DOCUMENTS}/simpleTrack.csv")
            val resultList = exportManager.importCSV(file)
            assertEquals(taskList.size, resultList.size)
        } catch (e: Exception) {
            assert(false)
        }
        scenario.close();
    }
}
