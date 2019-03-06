package com.example.rnv_examenandroid2evalu.ui.preferenciasFragment;

import android.os.Bundle;

import com.example.rnv_examenandroid2evalu.R;

import androidx.preference.PreferenceFragmentCompat;

public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
