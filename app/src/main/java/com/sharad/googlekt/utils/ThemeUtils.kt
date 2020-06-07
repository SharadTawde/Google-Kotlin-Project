package com.sharad.googlekt.utils

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

fun applyTheme(theme: Int) {
    AppCompatDelegate.setDefaultNightMode(theme)
}

fun AppCompatActivity.isDarkTheme(): Boolean {
    return (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)
}

fun isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 7 || currentHour >= 18)
}
