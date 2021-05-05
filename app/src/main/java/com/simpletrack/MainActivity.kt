package com.simpletrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simpletrack.model.Storage
import com.simpletrack.model.Task
import com.simpletrack.view.SettingsFragment
import com.simpletrack.view.TimerFragment
import com.simpletrack.view.ViewListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        val taskList = ArrayList<Task>()
        var currentTask: Task? = null
        lateinit var storage: Storage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storage = Storage(this)
        val timerFragment = TimerFragment()
        val viewListFragment = ViewListFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(timerFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_timer -> setCurrentFragment(timerFragment)
                R.id.navigation_viewList -> setCurrentFragment(viewListFragment)
                R.id.navigation_settings -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
