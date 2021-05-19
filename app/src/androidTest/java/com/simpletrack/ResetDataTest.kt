package com.simpletrack

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.simpletrack.model.Task
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.Locale

@RunWith(AndroidJUnit4::class)
@LargeTest
class ResetDataTest {
    @Test
    fun resetTest() {

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val storage = MainActivity.storage

        val taskList = arrayListOf(
            Task(
                LocalDateTime.of(2021, 3, 3, 10, 10, 45),
                LocalDateTime.of(2021, 3, 3, 12, 34, 50),
                "Eating"
            ),
            Task(
                LocalDateTime.of(2020, 3, 3, 10, 10, 45),
                LocalDateTime.of(2020, 3, 7, 12, 34, 50),
                "PARTYING"
            ),
            Task(
                LocalDateTime.of(2021, 1, 13, 15, 16, 0),
                LocalDateTime.of(2021, 1, 20, 13, 45, 50),
                "Studying"
            ),
            Task(
                LocalDateTime.of(2020, 12, 3, 0, 0, 45),
                LocalDateTime.of(2020, 12, 30, 7, 34, 50),
                "Programming"
            ),
            Task(
                LocalDateTime.of(2020, 3, 3, 10, 10, 45),
                LocalDateTime.of(2020, 5, 7, 12, 34, 50),
                "LOCKDOWNING"
            )
        )

        val success = storage.storeData(taskList)
        assertEquals(true, success)

        scenario.onActivity { activity ->
            Locale.setDefault(Locale.GERMAN)

            MainActivity.taskList = taskList
            activity.resetData()

            assertEquals(0, storage.loadData().size)
            assertEquals(0, MainActivity.taskList.size)
            assertEquals(Locale.ENGLISH, Locale.getDefault())
        }
    }
}
