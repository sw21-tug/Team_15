package com.simpletrack.model

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.simpletrack.MainActivity
import com.simpletrack.R
import java.util.Locale

class LanguageManager(val activity: MainActivity) {
    fun changeLanguage() {
        loadLocale()
        val languages = arrayOf("German", "Russian", "English")

        val langSelectorBuilder = AlertDialog.Builder(activity)
        langSelectorBuilder.setTitle(R.string.SelectLanguage)
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
            activity.recreateActivity()
            dialog.dismiss()
        }
        langSelectorBuilder.create().show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadLocale() {
        val sharedPref = activity.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet: String = sharedPref.getString("locale_to_set", "")!!
        setLocale(localeToSet)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setLocale(localeToSet: String) {
        val localeListToSet = LocaleList(Locale(localeToSet))
        LocaleList.setDefault(localeListToSet)
        activity.resources.configuration.setLocales(localeListToSet)
        activity.resources.updateConfiguration(activity.resources.configuration, activity.resources.displayMetrics)
        val sharedPref = activity.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("locale_to_set", localeToSet)
        sharedPref.apply()
    }
}
