/*
* Copyright (C) 2013 The OmniROM Project
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
*/
 
package dev.dylanakp.glyphify;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.UserHandle;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GlyphUtils {
    
    private static final String OPERATING = "/sys/devices/platform/soc/994000.i2c/i2c-0/0-003a/leds/led_strips/operating_mode";
    private static final String all_white_brightness = "/sys/class/leds/led_strips/all_white_brightness";

    private static int glyphBrightness = 255;
    private static boolean isGlyphTorchEnabled = false;

    // General

    public static String getAllWhiteBrightness() {
        return all_white_brightness;
    }

    public static void setOperating(int mode) {
        String glyphMode = String.valueOf(mode);
        if (FileUtils.fileWritable(OPERATING)) {
                FileUtils.writeValue(OPERATING, glyphMode);
                return;
        }
        return;
    }

    // Brightness

    public static void setBrightness(int brightness) {
        glyphBrightness = brightness;
        if (isGlyphTorchEnabled) {
            refreshGlyphTorch();
        }
        return;
    }

    public static int getBrightness() {
        return glyphBrightness;
    }

    // GlyphTorch

    public static boolean isGlyphTorchEnabled() {
        return isGlyphTorchEnabled;
    }

    public static void changeGlyphTorchBrightness() {
        FileUtils.writeValue(all_white_brightness, String.valueOf(glyphBrightness));
        return;
    }

    public static void switchGlyphTorch() {
        FileUtils.writeValue(all_white_brightness, isGlyphTorchEnabled ? "0" : String.valueOf(glyphBrightness));
        isGlyphTorchEnabled = !isGlyphTorchEnabled;
        return;
    }

    public static void refreshGlyphTorch() {
        FileUtils.writeValue(all_white_brightness, String.valueOf(glyphBrightness));
        return;
    }

}