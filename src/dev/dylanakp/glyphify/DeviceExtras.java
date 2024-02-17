/*
* Copyright (C) 2016 The OmniROM Project
* Copyright (C) 2021 The dot X Project
* Copyright (C) 2018-2021 crDroid Android Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package dev.dylanakp.glyphify;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import org.lineageos.support.preference.SwitchPreference;
import androidx.preference.TwoStatePreference;

import java.util.Arrays;

import dev.dylanakp.glyphify.FileUtils;
import dev.dylanakp.glyphify.R;

import org.lineageos.support.preference.CustomSeekBarPreference;

public class DeviceExtras extends PreferenceFragment {
    private static final String TAG = DeviceExtras.class.getSimpleName();

    public static final String KEY_SETTINGS_PREFIX = "device_setting_";

    public static final String KEY_GLYPHTORCH_SWITCH = "glyphtorch";

    public static final String KEY_GLYPHSTRENGTH_SWITCH = "glyphstrength";

    private static TwoStatePreference mGlyphTorchModeSwitch;

    private static CustomSeekBarPreference mGlyphStrengthPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        addPreferencesFromResource(R.xml.main);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        // Glyph Torch
        mGlyphTorchModeSwitch = (TwoStatePreference) findPreference(KEY_GLYPHTORCH_SWITCH);
        mGlyphTorchModeSwitch.setChecked(GlyphUtils.isGlyphTorchEnabled());
        mGlyphTorchModeSwitch.setOnPreferenceChangeListener(new GlyphTorchModeSwitch());

        // Glyph Strength
        mGlyphStrengthPreference = (GlyphStrengthPreference) findPreference(KEY_GLYPHSTRENGTH_SWITCH);
        if (mGlyphStrengthPreference != null) {
            mGlyphStrengthPreference.setEnabled(GlyphStrengthPreference.isSupported());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
    }

    private void removePref(Preference pref) {
        PreferenceGroup parent = pref.getParent();
        if (parent == null) {
            return;
        }
        parent.removePreference(pref);
        if (parent.getPreferenceCount() == 0) {
            removePref(parent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
