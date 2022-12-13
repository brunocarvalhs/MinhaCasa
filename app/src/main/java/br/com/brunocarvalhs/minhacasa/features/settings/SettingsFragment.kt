package br.com.brunocarvalhs.minhacasa.features.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import br.com.brunocarvalhs.minhacasa.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}