package com.simpletrack.model

import com.simpletrack.MainActivity
import java.io.*
import java.lang.Exception

class Storage(val activity: MainActivity) {

    companion object {
        val listFileName = "/TaskList"
    }

    fun storeData(taskList: ArrayList<Task>): Boolean {
        try {
            val directory: File = activity.filesDir
            val os = FileOutputStream(directory.path + listFileName)
            val oos = ObjectOutputStream(os)
            oos.writeObject(taskList)
            oos.close()
        } catch (e: Exception) {
            // TODO: handle different exceptions
            return false
        }
        return true
    }

    fun loadData(): ArrayList<Task> {
        try {
            val directory: File = activity.filesDir
            val os = FileInputStream(directory.path + listFileName)
            val oos = ObjectInputStream(os)
            return oos.readObject() as ArrayList<Task>
        } catch (e: Exception) {
            // TODO: handle different exceptions
            return ArrayList()
        }
    }
}
