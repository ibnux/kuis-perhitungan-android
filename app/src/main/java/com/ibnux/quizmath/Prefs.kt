package com.ibnux.quizmath

import android.content.Context
import android.content.SharedPreferences

object Prefs {

    val sharedPrefs: SharedPreferences =
        Aplikasi.context.getSharedPreferences("pengaturan", Context.MODE_PRIVATE)

    var benar: Int
        get() = sharedPrefs.getInt("benar",0)
        set(value) {
            sharedPrefs.edit().putInt("benar", value).apply()
        }

    var salah: Int
        get() = sharedPrefs.getInt("salah",0)
        set(value) {
            sharedPrefs.edit().putInt("salah", value).apply()
        }

    var level: Int
        get() = sharedPrefs.getInt("salah",0)
        set(value) {
            sharedPrefs.edit().putInt("salah", value).apply()
        }
}