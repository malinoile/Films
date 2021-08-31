package ru.malinoil.films.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentSettingsBinding

const val SETTINGS_PREFERENCES = "settings_prefs"
const val PREFERENCE_ADULT_NAME = "adult"

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val prefs by lazy {
        context?.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        prefs?.let {
            if (it.contains(PREFERENCE_ADULT_NAME)) {
                setCheckboxValue(it.getBoolean(PREFERENCE_ADULT_NAME, false))
            }
        }
    }

    override fun onPause() {
        prefs?.apply {
            edit().putBoolean(PREFERENCE_ADULT_NAME, binding.adultCheckBox.isChecked).apply()
        }
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setCheckboxValue(check: Boolean) {
        binding.adultCheckBox.isChecked = check
    }

}