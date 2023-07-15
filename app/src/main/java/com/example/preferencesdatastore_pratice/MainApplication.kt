package com.example.preferencesdatastore_pratice

import android.app.Application

class MainApplication : Application() {

    companion object{
        @get : Synchronized
        lateinit var instanceApplication: MainApplication
        private set
    }

    override fun onCreate() {
        super.onCreate()

        instanceApplication = this
    }
}