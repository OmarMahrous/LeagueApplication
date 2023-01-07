package com.digitalcreativity.leagueapplication

import android.app.Application
import com.digitalcreativity.leagueapplication.di.appModule
import com.digitalcreativity.leagueapplication.di.localDBModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LeagueApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init koin di
        startKoin {
            androidContext(this@LeagueApp)
            modules(listOf(appModule, localDBModule))
        }
    }

}