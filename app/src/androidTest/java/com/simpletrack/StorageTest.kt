package com.simpletrack

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.simpletrack.model.Task
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
@LargeTest
class StorageTest {
    @Test
    fun storeData() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val storage = MainActivity.storage
        val success = storage.storeData(
            arrayListOf(
                Task(LocalDateTime.of(2021, 3, 3, 10, 10, 45), LocalDateTime.of(2021, 3, 3, 12, 34, 50), "Eating"),
                Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 3, 7, 12, 34, 50), "PARTYING"),
                Task(LocalDateTime.of(2021, 1, 13, 15, 16, 0), LocalDateTime.of(2021, 1, 20, 13, 45, 50), "Studying"),
                Task(LocalDateTime.of(2020, 12, 3, 0, 0, 45), LocalDateTime.of(2020, 12, 30, 7, 34, 50), "Programming"),
                Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 5, 7, 12, 34, 50), "LOCKDOWNING")
            )
        )
        scenario.close()
        assert(success)
    }

    @Test
    fun loadData() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val storage = MainActivity.storage

        val taskList = arrayListOf(
            Task(LocalDateTime.of(2021, 3, 3, 10, 10, 45), LocalDateTime.of(2021, 3, 3, 12, 34, 50), "Eating"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 3, 7, 12, 34, 50), "PARTYING"),
            Task(LocalDateTime.of(2021, 1, 13, 15, 16, 0), LocalDateTime.of(2021, 1, 20, 13, 45, 50), "Studying"),
            Task(LocalDateTime.of(2020, 12, 3, 0, 0, 45), LocalDateTime.of(2020, 12, 30, 7, 34, 50), "Programming"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 5, 7, 12, 34, 50), "LOCKDOWNING")
        )

        val success = storage.storeData(taskList)

        assert(success)

        val loadedData = storage.loadData()

        scenario.close()
        assertEquals(taskList.size, loadedData.size)
    }

    @Test
    fun testStorageIntegration() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val storage = MainActivity.storage
        val inputSize = storage.loadData().size
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.stopButton)).perform(click())

        Thread.sleep(1000)
        val loadedData = storage.loadData()

        scenario.close()
        assertEquals(1, loadedData.size - inputSize)
    }

    @Test
    fun testDeleteData() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val storage = MainActivity.storage

        val taskList = arrayListOf(
            Task(LocalDateTime.of(2021, 3, 3, 10, 10, 45), LocalDateTime.of(2021, 3, 3, 12, 34, 50), "Eating"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 3, 7, 12, 34, 50), "PARTYING"),
            Task(LocalDateTime.of(2021, 1, 13, 15, 16, 0), LocalDateTime.of(2021, 1, 20, 13, 45, 50), "Studying"),
            Task(LocalDateTime.of(2020, 12, 3, 0, 0, 45), LocalDateTime.of(2020, 12, 30, 7, 34, 50), "Programming"),
            Task(LocalDateTime.of(2020, 3, 3, 10, 10, 45), LocalDateTime.of(2020, 5, 7, 12, 34, 50), "LOCKDOWNING")
        )

        val success = storage.storeData(taskList)

        assert(success)

        var loadedData = storage.loadData()

        assertEquals(taskList.size, loadedData.size)

        storage.deleteData()

        loadedData = storage.loadData()

        assertEquals(0, loadedData.size)

        scenario.close()
    }
}
