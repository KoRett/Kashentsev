package com.korett.kashentsev.app

import android.app.Application
import com.korett.kashentsev.di.AppComponent
import com.korett.kashentsev.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this).build()
    }

}