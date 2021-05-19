package com.simpletrack

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simpletrack.model.ExportManager
import com.simpletrack.model.Storage
import com.simpletrack.model.Task
import com.simpletrack.view.SettingsFragment
import com.simpletrack.view.TimerFragment
import com.simpletrack.view.ViewListFragment
import java.util.Locale

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storage = Storage(this)
        exportManager = ExportManager(this)
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
                R.id.language_selection -> changeLanguage()
            }
            true
        }

        loadLocale()
    }

    private fun changeLanguage() {
        loadLocale()
        val languages = arrayOf("German", "Russian", "English")

        val langSelectorBuilder = AlertDialog.Builder(this@MainActivity)
        langSelectorBuilder.setTitle("Select Language: ")
        langSelectorBuilder.setSingleChoiceItems(languages, -1) { dialog, selection ->
            when (selection) {
                0 -> {
                    setLocale("de")
                }
                1 -> {
                    setLocale("ru")
                }
                2 -> {
                    setLocale("en")
                }
            }
            recreateActivity()
            dialog.dismiss()
        }
        langSelectorBuilder.create().show()
    }

    // Call this method when you want to recreate this activity.
    private fun recreateActivity() {
        isActivityRecreated = true
        recreate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_ACTIVITY_RECREATED, isActivityRecreated)
        outState.putInt("PREV_FRAGMENT", currentFragment.ordinal)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setLocale(localeToSet: String) {
        val localeListToSet = LocaleList(Locale(localeToSet))
        LocaleList.setDefault(localeListToSet)
        resources.configuration.setLocales(localeListToSet)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("locale_to_set", localeToSet)
        sharedPref.apply()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadLocale() {
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet: String = sharedPref.getString("locale_to_set", "")!!
        setLocale(localeToSet)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
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
        setLocale("en")
        recreateActivity()
    }
}
