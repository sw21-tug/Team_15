package com.simpletrack

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simpletrack.view.HomeFragment
import com.simpletrack.view.SettingsFragment
import com.simpletrack.view.TimerFragment
import com.simpletrack.view.ViewListFragment
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val timerFragment = TimerFragment()
        val viewListFragment = ViewListFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment(homeFragment)
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

    fun setLanguage(context: Context, language: String): ContextWrapper {
        var mContext = context

        val localeLang = language.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val locale: Locale
        if (localeLang.size > 1)
            locale = Locale(localeLang[0], localeLang[1])
        else
            locale = Locale(localeLang[0])

        val res = mContext.resources
        val configuration = res.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            mContext = mContext.createConfigurationContext(configuration)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
            mContext = mContext.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            res.updateConfiguration(configuration, res.getDisplayMetrics())
        }

        return ContextWrapper(mContext)
    }

    //    For Language Changing
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            newBase?.let {
                setLanguage(
                    it,
                    "rus"
                )
            }
        )
    }
}
