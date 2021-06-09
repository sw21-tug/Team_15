package com.simpletrack

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simpletrack.model.ExportManager
import com.simpletrack.model.LanguageManager
import com.simpletrack.model.Storage
import com.simpletrack.model.Task
import com.simpletrack.view.SettingsFragment
import com.simpletrack.view.TimerFragment
import com.simpletrack.view.ViewListFragment
import java.util.Locale
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    enum class Fragments {
        TIMER, LIST, SETTINGS
    }

    // The key for saving and retrieving isActivityRecreated field.
    private val KEY_IS_ACTIVITY_RECREATED = "KEY_IS_ACTIVITY_RECREATED"

    /* true if this activity is recreated.  */
    private var isActivityRecreated = false

    private var currentFragment: Fragments = Fragments.TIMER

    companion object {
        var taskList = ArrayList<Task>()
        var currentTask: Task? = null
        lateinit var storage: Storage
        lateinit var exportManager: ExportManager
        lateinit var languageManager: LanguageManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storage = Storage(this)
        exportManager = ExportManager(this)
        languageManager = LanguageManager(this)
        taskList = storage.loadData()
        val timerFragment = TimerFragment()
        val viewListFragment = ViewListFragment()
        val settingsFragment = SettingsFragment()

        var loadFragment = Fragments.TIMER

        if (savedInstanceState != null) {
            isActivityRecreated = savedInstanceState.getBoolean(KEY_IS_ACTIVITY_RECREATED)
            if (isActivityRecreated) {
                // This activity has been recreated.
                // Reset the flag
                isActivityRecreated = false

                // Write your code when this activity recreated.
                loadFragment = Fragments.values()[savedInstanceState.getInt("PREV_FRAGMENT")]
            }
        }

        when (loadFragment) {
            Fragments.TIMER -> setCurrentFragment(timerFragment)
            Fragments.LIST -> setCurrentFragment(viewListFragment)
            Fragments.SETTINGS -> setCurrentFragment(settingsFragment)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_timer -> {
                    setCurrentFragment(timerFragment)
                    currentFragment = Fragments.TIMER
                }
                R.id.navigation_viewList -> {
                    setCurrentFragment(viewListFragment)
                    currentFragment = Fragments.LIST
                }
                R.id.navigation_settings -> {
                    setCurrentFragment(settingsFragment)
                    currentFragment = Fragments.SETTINGS
                }
            }
            true
        }

        languageManager.loadLocale()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_ACTIVITY_RECREATED, isActivityRecreated)
        outState.putInt("PREV_FRAGMENT", currentFragment.ordinal)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    // Call this method when you want to recreate this activity.
    fun recreateActivity() {
        isActivityRecreated = true
        recreate()
    }

    fun openResetDataDialogue() {
        val resetBuilder = AlertDialog.Builder(this@MainActivity)
        resetBuilder.setPositiveButton(R.string.yes) { dialog, _ ->
            resetData()
            dialog.dismiss()
        }
        resetBuilder.setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
        resetBuilder.setTitle(R.string.resetConfirmation)
        resetBuilder.create().show()
    }

    fun resetData() {
        storage.deleteData()
        taskList.clear()
        Locale.setDefault(Locale.ENGLISH)
        languageManager.setLocale("en")
        recreateActivity()
    }
}
