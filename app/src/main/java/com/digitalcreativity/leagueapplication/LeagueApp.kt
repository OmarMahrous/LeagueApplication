package com.digitalcreativity.leagueapplication

import android.app.Application
import android.content.Context
import com.digitalcreativity.leagueapplication.di.appModule
import com.digitalcreativity.leagueapplication.di.localDBModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LeagueApp : Application() {


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        // Init koin di
        startKoin {
            androidContext(this@LeagueApp)
            modules(listOf(appModule, localDBModule))
        }
    }

    companion object{
        private lateinit var appContext:Context

        public fun getAppContext(): Context {
            return appContext
        }
    }



}