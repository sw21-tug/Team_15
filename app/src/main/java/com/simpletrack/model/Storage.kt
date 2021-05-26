package com.simpletrack.model

import com.simpletrack.MainActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class Storage(val activity: MainActivity) {

    companion object {
        val listFileName = "/TaskList"
    }

    fun storeData(taskList: ArrayList<Task>): Boolean {
        return try {
            val directory: File = activity.filesDir
            val os = FileOutputStream(directory.path + listFileName, false)
            val oos = ObjectOutputStream(os)
            oos.writeObject(taskList)
            oos.close()
            true
        } catch (e: Exception) {
            // TODO: handle different exceptions
            false
        }
    }

    fun loadData(): ArrayList<Task> {
        return try {
            val directory: File = activity.filesDir
            val os = FileInputStream(directory.path + listFileName)
            val oos = ObjectInputStream(os)
            oos.readObject() as ArrayList<Task>
        } catch (e: Exception) {
            // TODO: handle different exceptions
            ArrayList()
        }
    }

    fun deleteData() {
        val localStorage = File(activity.filesDir.path + listFileName)
        if (localStorage.exists())
            localStorage.delete()
    }
}
