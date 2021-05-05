package com.simpletrack.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.simpletrack.R
import com.simpletrack.model.SettingsViewModel

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // https://stackoverflow.com/questions/58908518/not-able-to-change-language-locale-in-kotlin
        // https://stackoverflow.com/questions/50785840/change-locale-programmatically-in-kotlin
        // https://stackoverflow.com/questions/59713615/translation-of-ui-android-in-kotlin
        // TODO: Use the ViewModel
        requireView().findViewById<TextView>(R.id.currentLang).text = super.getResources().configuration.locale.toString()

        view?.findViewById<Button>(R.id.lang_rus)?.setOnClickListener {
            Handler().post {
                val intent = activity?.intent
                intent?.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                activity?.overridePendingTransition(0, 0)
                activity?.finish()

                activity?.overridePendingTransition(0, 0)
                startActivity(intent)
            }
            viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        }
    }
}
