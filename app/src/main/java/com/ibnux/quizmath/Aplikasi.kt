package com.ibnux.quizmath

import android.app.Application
import android.content.Context

class Aplikasi : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}