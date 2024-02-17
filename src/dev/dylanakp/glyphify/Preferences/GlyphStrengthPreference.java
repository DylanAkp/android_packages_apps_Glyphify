/*
* Copyright (C) 2016 The OmniROM Project
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
* along with this program. If not, see <http://www.gnu.com/licenses/>.
*
*/
package dev.dylanakp.glyphify;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import java.util.List;

public class GlyphStrengthPreference extends org.lineageos.support.preference.CustomSeekBarPreference {

    private static int mMinVal = 1;
    private static int mMaxVal = 255;
    private static int mDefVal = 255;

    public GlyphStrengthPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInterval = 1;
        mShowSign = false;
        mUnits = "";
        mContinuousUpdates = false;
        mMinValue = mMinVal;
        mMaxValue = mMaxVal;
        mDefaultValueExists = true;
        mDefaultValue = mDefVal;
        mValue = Integer.parseInt(loadValue());

        setPersistent(false);
    }

    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

        String storedValue = PreferenceManager.getDefaultSharedPreferences(context).getString(DeviceExtras.KEY_GLYPHSTRENGTH_SWITCH, String.valueOf(mDefVal));
        FileUtils.writeValue(String.valueOf(GlyphUtils.getBrightness()), storedValue);
    }

    public static String loadValue() {
        return FileUtils.getFileValue(String.valueOf(GlyphUtils.getBrightness()), String.valueOf(mDefVal));
    }

    public static boolean isSupported() {
        return FileUtils.fileWritable(GlyphUtils.getAllWhiteBrightness());
    }

    @Override
    protected void changeValue(int newValue) {
        GlyphUtils.setBrightness(newValue);
    }
}